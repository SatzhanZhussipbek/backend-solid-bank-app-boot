package com.example.repository.service;

import com.example.repository.entity.Account;

public interface AccountDepositService {
    public void deposit(double amount, Account account);
}
