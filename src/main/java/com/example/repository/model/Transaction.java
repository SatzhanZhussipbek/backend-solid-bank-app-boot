package com.example.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRANSACTION")
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transact_id")
    private long id;
    @Column(name = "client_id")
    private String clientID;
    @Column(name = "acc_id")
    private long accountID;
    @Column(name = "account_type")
    private AccountType accountType;
    @Column(name = "amount")
    private double amount;

    public Transaction(String clientID, long accountID, AccountType
            accountType, double amount) {
        this.clientID = clientID;
        this.accountID = accountID;
        this.accountType = accountType;
        this.amount = amount;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%s, account=%s, amount=%.2f, account type=%s]", clientID,
                accountID, amount, accountType);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}

