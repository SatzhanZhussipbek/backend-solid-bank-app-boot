package com.example.repository.model;

public class SavingAccount extends AccountWithdraw {
    public SavingAccount(AccountType accountType, long accountID, long id, long clientID, double balance, boolean withdrawAllowed) {
        super(accountType, accountID, id, clientID, balance, withdrawAllowed);
    }
}
