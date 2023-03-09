package com.example.repository.presentation;

import com.example.repository.entity.Account;
import com.example.repository.service.AccountListingService;
import com.example.repository.service.TransactionWithdraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionWithdrawCLI {
    private TransactionWithdraw transactionWithdraw;

    private WithdrawDepositOperationCLIUI withdrawDepositOperationCLIUI;

    private AccountListingService accountListing;
    @Autowired
    public TransactionWithdrawCLI(TransactionWithdraw transactionWithdraw,
                                  WithdrawDepositOperationCLIUI withdrawDepositOperationCLIUI,
                                  AccountListingService accountListing) {
        this.transactionWithdraw = transactionWithdraw;
        this.withdrawDepositOperationCLIUI = withdrawDepositOperationCLIUI;
        this.accountListing = accountListing;
    }

    public void withdrawMoney(long clientID) {
        String clientAccNum = withdrawDepositOperationCLIUI.requestClientAccountNumber();
        if (!String.valueOf(clientAccNum).matches("[0-9]*")) {
            System.out.println("The account ID is not valid.");
            return;
        }
        Account accountWithdraw = accountListing.getAccountThatWithdraw(clientID, Long.parseLong(clientAccNum));
        if (accountWithdraw == null) {
            System.out.println("The account doesn't exist.");
            return;
        }
        double targetAmount = withdrawDepositOperationCLIUI.requestClientAmount();
        if (targetAmount <= 0) {
            System.out.println("The amount can't be less than or equal to zero.");
            return;
        }
        if (accountWithdraw.getBalance() < targetAmount) {
            System.out.println("Not enough funds on your account.");
            return;
        }
        transactionWithdraw.execute(accountWithdraw, targetAmount);
    }
}

