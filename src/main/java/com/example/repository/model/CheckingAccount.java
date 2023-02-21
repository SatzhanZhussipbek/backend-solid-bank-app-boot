package com.example.repository.model;


public class CheckingAccount extends AccountWithdraw{
    public CheckingAccount(AccountType accountType, long accountID, long id, String clientID, double balance, boolean withdrawAllowed) {
        super(accountType, accountID, id, clientID, balance, withdrawAllowed);
    }
}
