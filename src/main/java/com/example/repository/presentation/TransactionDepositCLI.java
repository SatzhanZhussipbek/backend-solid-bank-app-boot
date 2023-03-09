package com.example.repository.presentation;

import com.example.repository.entity.Account;
import com.example.repository.service.AccountListingService;
import com.example.repository.service.TransactionDeposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionDepositCLI {
    private TransactionDeposit transactionDeposit;

    private WithdrawDepositOperationCLIUI withdrawDepositOperationCLIUI;

    private AccountListingService accountListing;
    @Autowired
    public TransactionDepositCLI(TransactionDeposit transactionDeposit,
                                 WithdrawDepositOperationCLIUI withdrawDepositOperationCLIUI,
                                 AccountListingService accountListing) {
        this.transactionDeposit = transactionDeposit;
        this.withdrawDepositOperationCLIUI = withdrawDepositOperationCLIUI;
        this.accountListing = accountListing;
    }
    public void depositMoney(long ClientID) {
        String accountNumber = withdrawDepositOperationCLIUI.requestClientAccountNumber();
        if (!String.valueOf(accountNumber).matches("[0-9]*")) {
            System.out.println("The account ID is not valid.");
            return;
        }
        Account clientAcc = accountListing.getClientAccount(ClientID, Long.parseLong(accountNumber));
        if (clientAcc == null) {
            System.out.println("The account doesn't exist.");
            return;
        }
        double targetAmount = withdrawDepositOperationCLIUI.requestClientAmount();
            if (targetAmount <= 0) {
                System.out.println("The amount can't be less than or equal to zero.");
                return;
            }
        transactionDeposit.execute(clientAcc, targetAmount);
    }
}
