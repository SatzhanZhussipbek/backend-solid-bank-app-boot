package com.example.repository.service;

import com.example.repository.model.AccountType;

public interface AccountCreationService {

    void create(AccountType accountType, long bankID, long clientID, long accountID);

    void delete(long accountID);
}
