package com.example.repository.service;

import com.example.repository.model.Account;

public interface AccountDepositService {
    public void deposit(double amount, Account account);
}
