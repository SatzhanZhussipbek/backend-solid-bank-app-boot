package com.example.repository.service;

import com.example.repository.entity.Account;

public interface AccountWithdrawService {
    public void withdraw(double amount, Account account);
}
