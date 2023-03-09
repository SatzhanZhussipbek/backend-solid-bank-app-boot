package com.example.repository.service;

import com.example.repository.entity.Account;
import com.example.repository.model.AccountType;

import java.util.List;

public interface AccountListingService {
    public Account getClientAccount(long clientID, long accountID);

    public Account getAccountThatWithdraw(long clientID, long accountID);

    public List<Account> getAccountsByClientID(long clientID);

    public List<Account> getAccountsByClientIDAndAccountType(long clientID, AccountType accountType);
}
