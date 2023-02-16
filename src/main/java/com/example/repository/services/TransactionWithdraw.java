package com.example.repository.services;

import com.example.repository.data.TransactionRepository;
import com.example.repository.entity.Account;
import com.example.repository.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionWithdraw {
    private AccountWithdrawService accountWithdrawService;

    private TransactionRepository transactionRepository;

    public TransactionWithdraw(AccountWithdrawService accountWithdrawService,
                               TransactionRepository transactionRepository) {
        this.accountWithdrawService = accountWithdrawService;
        this.transactionRepository = transactionRepository;
    }

    public void execute(Account accountWithdraw, double amount) {
        accountWithdrawService.withdraw(amount, accountWithdraw);
        Transaction newTransaction = new Transaction(accountWithdraw.getClientID(),
                accountWithdraw.getAccountID(), accountWithdraw.getAccountType(), amount);
        transactionRepository.save(newTransaction);
    }
}
