package com.example.repository.data;

import com.example.repository.entity.AccountType;
import com.example.repository.services.AccountCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankCore {
    private static long id = 1;

    private long lastAccountNumber = 1;

    private AccountCreationService accountCreation;
    @Autowired
    public BankCore(AccountCreationService accountCreation) {
        this.accountCreation = accountCreation;
    }

    public void createNewAccount(AccountType accountType, String clientID) {
        accountCreation.create(accountType, id, clientID, String.valueOf(lastAccountNumber));
        incrementLastAccountNumber();
    }

    private void incrementLastAccountNumber() {
        lastAccountNumber++;
    }
}
