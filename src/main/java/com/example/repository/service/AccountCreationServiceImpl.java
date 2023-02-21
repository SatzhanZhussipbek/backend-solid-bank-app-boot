package com.example.repository.service;

import com.example.repository.repository.AccountDAO;
import com.example.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreationServiceImpl implements AccountCreationService{
    private AccountDAO accountDAO;
    @Autowired
    public AccountCreationServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    @Override
    public void create(AccountType accountType, long bankID, String clientID, long accountID) {
        Account account = new Account();
        account.setAccountID(accountID); account.setAccountType(accountType);
        account.setBalance(0.0); account.setClientID(clientID);

        if (accountType.toString().equals("CHECKING") ) {
            account.setWithdrawAllowed(true);
            accountDAO.save(account);
            System.out.println("The bank account has been created");
        }
        else if (accountType.toString().equals("SAVING") ) {
            account.setWithdrawAllowed(true);
            accountDAO.save(account);
            System.out.println("The bank account has been created");
        }
        else if (accountType.toString().equals("FIXED")){
            account.setWithdrawAllowed(false);
            accountDAO.save(account);
            System.out.println("The bank account has been created");
        }
        else if (!accountType.toString().equals("CHECKING") && !accountType.toString().equals("SAVING") && !accountType.toString().equals("FIXED")) {
            System.out.println("Bank account was not created");
        }
    }
}
