package com.example.repository.model;

import com.example.repository.entity.Account;

public class AccountDeposit extends Account {
    public AccountDeposit(long id, long accountID, AccountType accountType, long clientID, double balance, boolean withdrawAllowed) {
        super(id, accountID, accountType, clientID, balance, withdrawAllowed);
    }
    public AccountDeposit() {

    }
}
