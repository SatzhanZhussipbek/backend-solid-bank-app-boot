package com.example.repository.presentation;

import org.springframework.stereotype.Component;

@Component
public interface WithdrawDepositOperationCLIUI{
    double requestClientAmount();

    String requestClientAccountNumber();
}
