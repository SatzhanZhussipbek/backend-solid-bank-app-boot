package com.example.repository.service;

import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.exceptions.AccountsNotFoundException;
import com.example.repository.repository.AccountDAO;
import com.example.repository.model.Account;
import com.example.repository.model.AccountType;
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
    public Account getClientAccount(String clientID, long accountID) {
        if (accountDAO.getAccountByClientIDAndAccountID(clientID, accountID) != null) {
            return accountDAO.getAccountByClientIDAndAccountID(clientID, accountID);
        }
        throw new AccountNotFoundException(accountID);
    }

    @Override
    public Account getAccountThatWithdraw(String clientID, long accountID) {
        Account account = null;
        if (accountDAO.findById(accountID).isPresent()) {
            if (accountDAO.getAccountByClientIDAndAccountID(clientID, accountID).isWithdrawAllowed()) {
                account = accountDAO.getAccountByClientIDAndAccountID(clientID, accountID);
            } else {
                System.out.println("You can't withdraw money from a fixed account.");
            }
            return account;
        }
        throw new AccountNotFoundException(accountID);
    }

    @Override
    public List<Account> getAccountsByClientID(String clientID) {
        if (!accountDAO.getAccountsByClientID(clientID).isEmpty()) {
            List<Account> list = new ArrayList<>(accountDAO.getAccountsByClientID(clientID));
            return list;
        }
        throw new AccountsNotFoundException(Long.parseLong(clientID));
    }

    @Override
    public List<Account> getAccountsByClientIDAndAccountType(String clientID, AccountType accountType) {
        return accountDAO.getAccountsByClientIDAndAccountType(clientID, accountType);
    }
}
