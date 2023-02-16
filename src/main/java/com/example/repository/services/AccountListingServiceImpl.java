package com.example.repository.services;

import com.example.repository.data.AccountDAO;
import com.example.repository.entity.Account;
import com.example.repository.entity.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AccountListingServiceImpl implements AccountListingService{

    private AccountDAO accountDAO;
    @Autowired
    public AccountListingServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account getClientAccount(String clientID, String accountID) {
        return accountDAO.getAccountByClientIDAndAccountID(clientID, accountID);
    }

    @Override
    public Account getAccountThatWithdraw(String clientID, String accountID) {
        Account account = null;
        if (accountDAO.getAccountByClientIDAndAccountID(clientID, accountID) != null) {
            if (accountDAO.getAccountByClientIDAndAccountID(clientID, accountID).isWithdrawAllowed()) {
                account = accountDAO.getAccountByClientIDAndAccountID(clientID, accountID);
            }
            else {
                System.out.println("You can't withdraw money from a fixed account.");
            }
        }
        return account;
    }

    @Override
    public List<Account> getAccountsByClientID(String clientID) {
        List<Account> list = new ArrayList<>();
        if (accountDAO.getAccountsByClientID(clientID) != null) {
            list.addAll(accountDAO.getAccountsByClientID(clientID));
            return list;
        }
        return list;
    }

    @Override
    public List<Account> getAccountsByClientIDAndAccountType(String clientID, AccountType accountType) {
        return accountDAO.getAccountsByClientIDAndAccountType(clientID, accountType);
    }
}
