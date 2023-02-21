package com.example.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "account_id")
    private long accountID;
    @Column(name = "account_type")
    private AccountType accountType;
    @Column(name = "client_id")
    private String clientID;
    @Column(name = "balance")
    private double balance;
    @Column(name = "withdraw_allowed")
    private boolean withdrawAllowed;

    public Account() {

    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isWithdrawAllowed() {
        return withdrawAllowed;
    }

    public void setWithdrawAllowed(boolean withdrawAllowed) {
        this.withdrawAllowed = withdrawAllowed;
    }

    @Override
    public String toString() {
        return String.format("Account{id='%03d%06d', clientID='%s', balance='%.1f'}", 1, accountID, clientID, balance);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
