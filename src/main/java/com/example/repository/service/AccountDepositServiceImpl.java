package com.example.repository.service;

import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.entity.Transaction;
import com.example.repository.repository.AccountRepository;
import com.example.repository.entity.Account;
import com.example.repository.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositServiceImpl implements AccountDepositService{
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    @Autowired
    public AccountDepositServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void deposit(double amount, Account account) {
        if (accountRepository.getAccountByClientIDAndAccountID(account.getClientID(), account.getAccountID()) != null) {
            accountRepository.updateAccountSetBalance((account.getBalance() + amount), account.getAccountID());
            Transaction newTransaction = new Transaction(account.getClientID(),
                    account.getAccountID(), account.getAccountType(), amount);
            transactionRepository.save(newTransaction);
        }
        else {
            throw new AccountNotFoundException(account.getAccountID());
        }
    }
}
