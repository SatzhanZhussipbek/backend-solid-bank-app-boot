package com.example.repository.service;

import com.example.repository.entity.Account;

public interface AccountTransferService {
    public void transfer(double amount, long accountID, long destinationAccount);
}
