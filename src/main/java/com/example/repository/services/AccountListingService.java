package com.example.repository.services;

import com.example.repository.entity.Account;
import com.example.repository.entity.AccountType;

import java.util.List;

public interface AccountListingService {
    public Account getClientAccount(String clientID, String accountID);

    public Account getAccountThatWithdraw(String clientID, String accountID);

    public List<Account> getAccountsByClientID(String clientID);

    public List<Account> getAccountsByClientIDAndAccountType(String clientID, AccountType accountType);
}
