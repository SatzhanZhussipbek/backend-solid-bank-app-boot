package com.example.repository.service;

import com.example.repository.entity.Account;
import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.repository.AccountRepository;
import com.example.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreationServiceImpl implements AccountCreationService{
    private AccountRepository accountRepository;
    @Autowired
    public AccountCreationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public void create(AccountType accountType, long bankID, long clientID, long accountID) {
        if (accountRepository.existsById(accountID) ) {
            System.out.println("There is already an account with the same id");
        }
        Account account = new Account();
        account.setAccountID(accountID); account.setAccountType(accountType);
        account.setBalance(0.0); account.setClientID(clientID);
        if (accountType.toString().equals("CHECKING") || accountType.toString().equals("SAVING") ) {
            account.setWithdrawAllowed(true);
            accountRepository.save(account);
            System.out.println("The bank account has been created");
        }
        else if (accountType.toString().equals("FIXED")){
            account.setWithdrawAllowed(false);
            accountRepository.save(account);
            System.out.println("The bank account has been created");
        }
        else if (!accountType.toString().equals("CHECKING") && !accountType.toString().equals("SAVING") && !accountType.toString().equals("FIXED")) {
            System.out.println("Bank account has not been created");
        }
    }

    @Override
    public void delete(long accountID) {
        accountRepository.findById(accountID).orElseThrow(() -> new AccountNotFoundException(accountID));
        accountRepository.deleteById(accountID);
    }
}
