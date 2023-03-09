package com.example.repository.presentation;

import com.example.repository.service.BankCore;
import com.example.repository.entity.Account;
import com.example.repository.model.AccountType;
import com.example.repository.service.AccountListingService;
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

    public void createAccountRequest(long clientID) {
        AccountType accountType = createAccountOperationUI.requestAccountType();
        if (accountType==null) {
            return;
        }
        bankCore.createNewAccount(accountType, clientID);
    }

    public void getAccounts(long clientID) {
        List<Account> listOfAccounts = accountListing.getAccountsByClientID(clientID);
        System.out.println(listOfAccounts);
    }
}
