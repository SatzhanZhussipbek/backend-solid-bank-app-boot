package com.example.repository.services;

import com.example.repository.data.AccountDAO;
import com.example.repository.data.TransactionRepository;
import com.example.repository.entity.Account;
import com.example.repository.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositServiceImpl implements AccountDepositService{
    private AccountDAO accountDAO;

    private TransactionRepository transactionRepository;
    @Autowired
    public AccountDepositServiceImpl(AccountDAO accountDAO, TransactionRepository transactionRepository) {
        this.accountDAO = accountDAO;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void deposit(double amount, Account account) {
        System.out.printf("%.2f$ transferred to %03d%06d account\n", amount, 1, Long.parseLong(account.getAccountID()));
        accountDAO.updateAccountSetBalance( (account.getBalance()+amount), account.getAccountID() );
    }
}
