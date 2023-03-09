package com.example.repository.model;

import com.example.repository.entity.Account;

public class AccountWithdraw extends Account {
    public AccountWithdraw(AccountType accountType, long accountID, long id, long clientID, double balance, boolean withdrawAllowed) {
        super(id, accountID, accountType, clientID, balance, withdrawAllowed);
    }
}
