package com.example.repository.services;

import com.example.repository.data.AccountDAO;
import com.example.repository.entity.*;
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
    public void create(AccountType accountType, long bankID, String clientID, String accountID) {
        Account account = new Account();
        account.setAccountID(accountID); account.setAccountType(accountType);
        account.setBalance(0.0); account.setClientID(clientID);

        if (accountType.toString().equals("CHECKING") ) {
            account.setWithdrawAllowed(true);
        }
        else if (accountType.toString().equals("SAVING") ) {
            account.setWithdrawAllowed(true);
        }
        else if (accountType.toString().equals("FIXED")){
            account.setWithdrawAllowed(false);
        }
        System.out.println("The bank account has been created");
        accountDAO.save(account);
    }
}
