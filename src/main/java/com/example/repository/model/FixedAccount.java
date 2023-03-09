package com.example.repository.model;

public class FixedAccount extends AccountDeposit {
    public FixedAccount(long id, long accountID, AccountType accountType, long clientID, double balance, boolean withdrawAllowed) {
        super(id, accountID, accountType, clientID, balance, withdrawAllowed);
    }
}
