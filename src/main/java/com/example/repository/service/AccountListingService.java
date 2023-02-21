package com.example.repository.service;

import com.example.repository.model.Account;
import com.example.repository.model.AccountType;

import java.util.List;

public interface AccountListingService {
    public Account getClientAccount(String clientID, long accountID);

    public Account getAccountThatWithdraw(String clientID, long accountID);

    public List<Account> getAccountsByClientID(String clientID);

    public List<Account> getAccountsByClientIDAndAccountType(String clientID, AccountType accountType);
}
