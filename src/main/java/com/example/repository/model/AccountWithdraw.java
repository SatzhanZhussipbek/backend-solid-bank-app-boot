package com.example.repository.model;

public class AccountWithdraw extends Account {
    public AccountWithdraw(AccountType accountType, long accountID, long id, String clientID, double balance, boolean withdrawAllowed) {
        super(id, accountID, accountType, clientID, balance, withdrawAllowed);
    }
}
