package com.example.repository.service;

import com.example.repository.model.Account;

public interface AccountWithdrawService {
    public void withdraw(double amount, Account account);
}
