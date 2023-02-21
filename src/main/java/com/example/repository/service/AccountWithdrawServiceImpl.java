package com.example.repository.service;

import com.example.repository.repository.AccountDAO;
import com.example.repository.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountWithdrawServiceImpl implements AccountWithdrawService{
    private AccountDAO accountDAO;
    @Autowired
    public AccountWithdrawServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    @Override
    public void withdraw(double amount, Account account) {
        System.out.printf("%.2f$ transferred from %03d%06d account\n", amount, 1, account.getAccountID());
        accountDAO.updateAccountSetBalance(account.getBalance()-amount, account.getAccountID());
    }
}
