package com.example.repository.service;

import com.example.repository.repository.TransactionRepository;
import com.example.repository.entity.Account;
import com.example.repository.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDeposit {
    private AccountDepositService accountDepositService;

    private TransactionRepository transactionRepository;

    public TransactionDeposit(AccountDepositService accountDepositService, TransactionRepository transactionRepository) {
        this.accountDepositService = accountDepositService;
        this.transactionRepository = transactionRepository;
    }

    public void execute(Account accountDeposit, double amount) {
        accountDepositService.deposit(amount, accountDeposit);
        Transaction newTransaction = new Transaction(accountDeposit.getClientID(),
                accountDeposit.getAccountID(), accountDeposit.getAccountType(), amount);
        transactionRepository.save(newTransaction);
    }
}
