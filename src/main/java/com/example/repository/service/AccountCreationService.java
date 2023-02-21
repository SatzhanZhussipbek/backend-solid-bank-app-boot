package com.example.repository.service;

import com.example.repository.model.AccountType;

public interface AccountCreationService {

    void create(AccountType accountType, long bankID, String clientID, long accountID);
}
