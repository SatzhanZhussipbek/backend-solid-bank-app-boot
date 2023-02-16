package com.example.repository.services;

import com.example.repository.entity.Account;

public interface AccountWithdrawService {
    public void withdraw(double amount, Account account);
}
