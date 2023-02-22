package com.example.repository.controller;
import java.util.List;

import com.example.repository.service.BankCore;
import com.example.repository.repository.TransactionRepository;
import com.example.repository.model.Account;
import com.example.repository.model.Transaction;
import com.example.repository.exceptions.*;
import com.example.repository.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
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
    BankCore bankCore;

    // Получение списка всех счетов клиента ( + )
    @GetMapping
    public ResponseEntity<Object> getAccounts() {
        String clientID = "1";
        return ResponseEntity.ok(accountListingService.getAccountsByClientID(clientID));
    }
    // Получение информации об одном счете ( + )
    @GetMapping("/{accountID}")
    public ResponseEntity<Object> getAccount(@PathVariable long accountID) {
        return ResponseEntity.ok(accountListingService.getClientAccount("1", accountID));
    }
    // Получение списка всех транзакций ( + )
    @GetMapping("/{accountID}/transactions")
    @ResponseBody
    public List<Transaction> getTransactions(@PathVariable long accountID) {
        Account acc = accountListingService.getClientAccount("1", accountID);
        if (acc != null) {
            if (!transactionRepository.getTransactionsByAccountID(accountID).isEmpty()) {
                return transactionRepository.getTransactionsByAccountID(accountID);
            }
            throw new TransactionsNotFoundException(accountID);
        }
        throw new AccountNotFoundException(accountID);
    }
    // Создание нового счета ( + )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        bankCore.createNewAccount(account.getAccountType(), "1");
        return new ResponseEntity<>(new ErrorTemplate(String.format("Account %s created",
                bankCore.getLastAccountNumber()-1 ) ),
        HttpStatus.CREATED);
    }
    // Удаление счета ( + )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        accountCreationService.delete(id);
        return new ResponseEntity<>(new ErrorTemplate(String.format("Account %d deleted", id) ),
                    HttpStatus.OK);
    }
    // Внесение денег на счет ( + )
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Object> depositAccount(@PathVariable long id, @RequestParam Double amount) {
        Account depositAccount = accountListingService.getClientAccount("1", id);
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
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawAccount(@PathVariable long id, @RequestParam Double amount) {
        Account withdrawAccount = accountListingService.getClientAccount("1", id);
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
}
