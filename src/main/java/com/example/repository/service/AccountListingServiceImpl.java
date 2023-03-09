package com.example.repository.service;

import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.exceptions.AccountsNotFoundException;
import com.example.repository.repository.AccountRepository;
import com.example.repository.entity.Account;
import com.example.repository.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AccountListingServiceImpl implements AccountListingService{

    private AccountRepository accountRepository;
    @Autowired
    public AccountListingServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getClientAccount(long clientID, long accountID) {
        if (accountRepository.getAccountByClientIDAndAccountID(clientID, accountID) != null) {
            return accountRepository.getAccountByClientIDAndAccountID(clientID, accountID);
        }
        throw new AccountNotFoundException(accountID);
    }

    @Override
    public Account getAccountThatWithdraw(long clientID, long accountID) {
        Account account = null;
        if (accountRepository.findById(accountID).isPresent()) {
            if (accountRepository.getAccountByClientIDAndAccountID(clientID, accountID).isWithdrawAllowed()) {
                account = accountRepository.getAccountByClientIDAndAccountID(clientID, accountID);
            } else {
                System.out.println("You can't withdraw money from a fixed account.");
            }
            return account;
        }
        throw new AccountNotFoundException(accountID);
    }

    @Override
    public List<Account> getAccountsByClientID(long clientID) {
        //if (!accountRepository.getAccountsByClientID(clientID).isEmpty()) {
            List<Account> list = new ArrayList<>(accountRepository.getAccountsByClientID(clientID));
            return list;
        //}
        //throw new AccountsNotFoundException(Long.parseLong(clientID));
    }

    @Override
    public List<Account> getAccountsByClientIDAndAccountType(long clientID, AccountType accountType) {
        return accountRepository.getAccountsByClientIDAndAccountType(clientID, accountType);
    }
}
