package com.example.repository.services;

import com.example.repository.entity.AccountType;

public interface AccountCreationService {

    void create(AccountType accountType, long bankID, String clientID, String accountID);
}
