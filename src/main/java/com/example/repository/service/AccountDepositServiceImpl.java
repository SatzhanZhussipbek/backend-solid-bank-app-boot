package com.example.repository.service;

import com.example.repository.repository.AccountDAO;
import com.example.repository.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositServiceImpl implements AccountDepositService{
    private AccountDAO accountDAO;
    @Autowired
    public AccountDepositServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void deposit(double amount, Account account) {
        if (!String.valueOf(account.getAccountID()).matches("[0-9]*")) {
            System.out.println("The account ID is not valid.");
            return;
        }
        if (amount <= 0) {
            System.out.println("The amount can't be less than or equal to zero.");
            return;
        }
        System.out.printf("%.2f$ transferred to %03d%06d account\n", amount, 1, account.getAccountID());
        accountDAO.updateAccountSetBalance( (account.getBalance()+amount), account.getAccountID() );
    }
}
