package com.example.repository.controller;
import java.util.List;
import com.example.repository.repository.AccountDAO;
import com.example.repository.model.BankCore;
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
    private AccountDAO accountDAO;

    @Autowired
    BankCore bankCore;

    // Получение списка всех счетов клиента ( + )
    @GetMapping
    public ResponseEntity<Object> getAccounts() {
        String clientID = "1";
        if (!accountListingService.getAccountsByClientID(clientID).isEmpty()) {
            return ResponseEntity.ok(accountListingService.getAccountsByClientID(clientID));
        }
        throw new AccountsNotFoundException(Long.parseLong(clientID));
    }
    // Получение информации об одном счете ( + )
    @GetMapping("/{accountID}")
    public ResponseEntity<Object> getAccount(@PathVariable long accountID) {
        Account searchedAccount = accountListingService.getClientAccount("1", accountID);
        if (searchedAccount != null) {
            return ResponseEntity.ok(accountListingService.getClientAccount("1", accountID));
        }
        throw new AccountNotFoundException(accountID);
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
        if (account == accountListingService.getClientAccount("1", account.getAccountID())) {
            return new ResponseEntity<>(new ErrorTemplate("There is already an account with the same id"),
            HttpStatus.BAD_REQUEST);
        }
        bankCore.createNewAccount(account.getAccountType(), "1");
        return new ResponseEntity<>(new ErrorTemplate(String.format("Account %s created",
                bankCore.getLastAccountNumber()-1 ) ),
        HttpStatus.CREATED);
    }
    // Удаление счета ( + )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        if (accountListingService.getClientAccount("1", id) != null) {
            accountDAO.deleteById(String.valueOf(id));
            return new ResponseEntity<>(new ErrorTemplate(String.format("Account %d deleted", id) ),
                    HttpStatus.OK);
        }
        throw new AccountNotFoundException(id);
    }
    // Внесение денег на счет ( + )
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Object> depositAccount(@PathVariable long id, @RequestParam Double amount) {
        Account depositAccount = accountListingService.getClientAccount("1", id);
        if (depositAccount != null && amount > 0) {
            accountDepositService.deposit(amount, depositAccount);
            Transaction newTransaction = new Transaction(depositAccount.getClientID(), depositAccount.getAccountID(),
                    depositAccount.getAccountType(), amount);
            transactionRepository.save(newTransaction);
            return new ResponseEntity<>(new ErrorTemplate(String.format("%.2f$ transferred to %03d%06d account\n", amount, 1,
                            depositAccount.getAccountID()) ),
                    HttpStatus.OK);
        }
        else if (depositAccount != null && amount <= 0) {
            return new ResponseEntity<>(new ErrorTemplate("The amount can't be less than or equal to zero."),
                    HttpStatus.BAD_REQUEST);
        }
        throw new AccountNotFoundException(id);
    }
    // Снятие денег со счета ( + )
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawAccount(@PathVariable long id, @RequestParam Double amount) {
        Account withdrawAccount = accountListingService.getClientAccount("1", id);
        if (withdrawAccount != null && withdrawAccount.isWithdrawAllowed() && amount > 0) {
            if (withdrawAccount.getBalance() > amount) {
                accountWithdrawService.withdraw(amount, withdrawAccount);
                Transaction newTransaction = new Transaction(withdrawAccount.getClientID(), withdrawAccount.getAccountID(),
                        withdrawAccount.getAccountType(), amount);
                transactionRepository.save(newTransaction);
                return new ResponseEntity<>(new ErrorTemplate(String.format("%.2f$ transferred from %03d%06d account\n", amount, 1,
                        withdrawAccount.getAccountID())),
                        HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new ErrorTemplate("Not enough funds on your account."),
                        HttpStatus.BAD_REQUEST);
            }
        }
        else if (withdrawAccount != null && !withdrawAccount.isWithdrawAllowed()) {
            return new ResponseEntity<>(new ErrorTemplate("You can't withdraw money from a fixed account."),
                    HttpStatus.BAD_REQUEST);
        }
        throw new AccountNotFoundException(id);
    }
}
