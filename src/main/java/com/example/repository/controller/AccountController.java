package com.example.repository.controller;
import java.security.Principal;
import java.util.List;

import com.example.repository.entity.Person;
import com.example.repository.model.JwtRequest;
import com.example.repository.model.TransferRequest;
import com.example.repository.model.UserDAO;
import com.example.repository.repository.AccountRepository;
import com.example.repository.repository.PersonRepository;
import com.example.repository.security.AccountUserDetails;
import com.example.repository.security.AccountUserDetailsService;
import com.example.repository.service.BankCore;
import com.example.repository.repository.TransactionRepository;
import com.example.repository.entity.Account;
import com.example.repository.entity.Transaction;
import com.example.repository.exceptions.*;
import com.example.repository.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
//@SecurityRequirement(name = "basicauth")
@Slf4j
public class AccountController {
    @Autowired
    private AccountListingService accountListingService;
    @Autowired
    private AccountDepositService accountDepositService;
    @Autowired
    private AccountWithdrawService accountWithdrawService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountCreationService accountCreationService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    BankCore bankCore;
    @Autowired
    private UserCreationService userCreationService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/information")
    public UserDAO getInfo(@RequestParam String username) {
        Person person = personRepository.getPersonByClientName(username);
        if (person == null) {
            throw new UsernameNotFoundException("There is no user with such name.");
        }
        return new UserDAO(person.getClientID(), person.getClientName());
    }

    // Получение списка всех счетов клиента ( + )
    @GetMapping("/accounts")
    public ResponseEntity<Object> getAccounts(@RequestParam long clientID, Authentication authentication) {
        Person person = personRepository.getPersonByClientID(clientID);
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        return ResponseEntity.ok(accountListingService.getAccountsByClientID(clientID));
    }
    // Получение информации об одном счете ( + )

    @GetMapping("/accounts/{accountID}")
    public ResponseEntity<Object> getAccount(@PathVariable long accountID,
                                             @RequestParam long clientID, Authentication authentication) {
        Person person = personRepository.getPersonByClientID(clientID);
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        return ResponseEntity.ok(accountListingService.getClientAccount(clientID, accountID));
    }
    // Получение списка всех транзакций ( + )

    @GetMapping("/accounts/{accountID}/transactions")
    public List<Transaction> getTransactions(@PathVariable long accountID,
                                             @RequestParam long clientID, Authentication authentication) {
        Person person = personRepository.getPersonByClientID(clientID);
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        Account acc = accountListingService.getClientAccount(clientID, accountID);
        if (acc != null) {
            if (!transactionRepository.getTransactionsByAccountID(accountID).isEmpty()) {
                return transactionRepository.getTransactionsByAccountID(accountID);
            }
            throw new TransactionsNotFoundException(accountID);
        }
        throw new AccountNotFoundException(accountID);
    }
    // Создание нового счета ( + )

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createAccount(@RequestBody Account account, Authentication authentication) {
        Person person = personRepository.getPersonByClientID(account.getClientID());
        /*log.error("----->"+authentication.isAuthenticated());
        log.error("----->"+authentication.getCredentials()+" "+authentication.getPrincipal());*/
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        bankCore.createNewAccount(account.getAccountType(), account.getClientID());
        return new ResponseEntity<>(new ErrorTemplate(String.format("Account %s created",
                bankCore.getLastAccountNumber()-1 ) ),
        HttpStatus.CREATED);
    }
    // Удаление счета ( + )

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id, @RequestParam long clientID,
                                                Authentication authentication) {
        Person person = personRepository.getPersonByClientID(clientID);
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        accountCreationService.delete(id);
        return new ResponseEntity<>(new ErrorTemplate(String.format("Account %d deleted", id) ),
                    HttpStatus.OK);
    }
    // Внесение денег на счет ( + )

    @PutMapping("/accounts/{id}/deposit")
    public ResponseEntity<Object> depositAccount(@PathVariable long id, @RequestParam Double amount,
                                                 Authentication authentication) {
        Account account1 = accountRepository.getAccountByAccountID(id);
        Person person = personRepository.getPersonByClientID(account1.getClientID());
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        Account depositAccount = accountListingService.getClientAccount(account1.getClientID(), id);
        if (amount > 0) {
            accountDepositService.deposit(amount, depositAccount);
            return new ResponseEntity<>(new ErrorTemplate(String.format("%.2f$ transferred to %03d%06d account\n", amount, 1,
                            depositAccount.getAccountID()) ),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorTemplate("The amount can't be less than or equal to zero."),
                    HttpStatus.BAD_REQUEST);
    }
    // Снятие денег со счета ( + )

    @PutMapping("/accounts/{id}/withdraw")
    public ResponseEntity<?> withdrawAccount(@PathVariable long id, @RequestParam Double amount,
                                             Authentication authentication) {
        Account clientAccount = accountRepository.getAccountByAccountID(id);
        Person person = personRepository.getPersonByClientID(clientAccount.getClientID());
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        Account withdrawAccount = accountListingService.getClientAccount(clientAccount.getClientID(), id);
        if (amount > 0 && withdrawAccount.isWithdrawAllowed()) {
            if (withdrawAccount.getBalance() > amount) {
                accountWithdrawService.withdraw(amount, withdrawAccount);
                return new ResponseEntity<>(new ErrorTemplate(String.format("%.2f$ transferred from %03d%06d account\n", amount, 1,
                        withdrawAccount.getAccountID())),
                        HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ErrorTemplate("Not enough funds on your account."),
                        HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ErrorTemplate("You can't withdraw money from a fixed account."),
                    HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/accounts/{accountID}/transfer")
    public ResponseEntity<?> transfer (@PathVariable Long accountID, @RequestParam double amount,
                                       @RequestBody TransferRequest transferRequest,
                                       Authentication authentication) {
        Person person = personRepository.getPersonByClientID(
                accountRepository.getAccountByAccountID(accountID).getClientID());
        if (!authentication.getPrincipal().equals(person.getClientName()) ||
                !authentication.getCredentials().equals(person.getPassword()) )  {
            throw new AccessDeniedException("Access denied. You entered the wrong id. ");
        }
        accountTransferService.transfer(amount, accountID,
                transferRequest.getDestinationAccountID());
        return new ResponseEntity<>(new ErrorTemplate(String.format(
                "%.2f$ transferred from %03d%06d account to %03d%06d",
                amount, 1, accountID, 1, transferRequest.getDestinationAccountID())), HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateData (@RequestBody JwtRequest jwtRequest) throws Exception {
        return ResponseEntity.ok(userCreationService.authenticateUser(jwtRequest.getUsername(),
                jwtRequest.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(userCreationService.register(jwtRequest.getUsername(), jwtRequest.getPassword()));
    }


}
