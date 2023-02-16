package com.example.repository.services;

import com.example.repository.entity.Account;

public interface AccountDepositService {
    public void deposit(double amount, Account account);
}
