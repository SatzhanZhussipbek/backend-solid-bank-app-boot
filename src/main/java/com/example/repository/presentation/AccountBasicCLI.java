package com.example.repository.presentation;

import com.example.repository.data.BankCore;
import com.example.repository.entity.Account;
import com.example.repository.entity.AccountType;
import com.example.repository.services.AccountListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountBasicCLI {
    @Autowired
    public AccountBasicCLI(CreateAccountOperationUI createAccountOperationUI, BankCore bankCore, AccountListingService accountListing) {
        this.createAccountOperationUI = createAccountOperationUI;
        this.bankCore = bankCore;
        this.accountListing = accountListing;
    }

    private CreateAccountOperationUI createAccountOperationUI;

    private BankCore bankCore;

    private AccountListingService accountListing;

    public void createAccountRequest(String clientID) {
        AccountType accountType = createAccountOperationUI.requestAccountType();
        if (accountType==null) {
            return;
        }
        bankCore.createNewAccount(accountType, clientID);
    }

    public void getAccounts(String clientID) {
        List<Account> listOfAccounts = accountListing.getAccountsByClientID(clientID);
        System.out.println(listOfAccounts);
    }
}
