package com.example.repository.service;

import com.example.repository.entity.Account;
import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountTransferServiceImpl implements AccountTransferService{

    private AccountRepository accountRepository;

    private AccountDepositService accountDepositService;

    private AccountWithdrawService accountWithdrawService;
    @Autowired
    public AccountTransferServiceImpl(AccountRepository accountRepository,
                                      AccountDepositService accountDepositService,
                                      AccountWithdrawService accountWithdrawService) {
        this.accountRepository = accountRepository;
        this.accountDepositService = accountDepositService;
        this.accountWithdrawService = accountWithdrawService;
    }
    @Override
    public void transfer(double amount, long accountID, long destinationAccount) {
        Account account1 = accountRepository.getAccountByAccountID(accountID);
        Account account2 = accountRepository.getAccountByAccountID(destinationAccount);
        if (account1 != null && account2 != null) {
            accountWithdrawService.withdraw(amount, account1);
            accountDepositService.deposit(amount, account2);
        }
        else {
            throw new AccountNotFoundException(destinationAccount);
        }
    }
}
