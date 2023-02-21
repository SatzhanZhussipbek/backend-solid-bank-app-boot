package com.example.repository.model;

public class AccountDeposit extends Account {
    public AccountDeposit(long id, long accountID, AccountType accountType, String clientID, double balance, boolean withdrawAllowed) {
        super(id, accountID, accountType, clientID, balance, withdrawAllowed);
    }
    public AccountDeposit() {

    }
}
