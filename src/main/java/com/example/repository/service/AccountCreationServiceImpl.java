package com.example.repository.service;

import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.exceptions.ErrorTemplate;
import com.example.repository.repository.AccountDAO;
import com.example.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.channels.AcceptPendingException;

@Component
public class AccountCreationServiceImpl implements AccountCreationService{
    private AccountDAO accountDAO;
    @Autowired
    public AccountCreationServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    @Override
    public void create(AccountType accountType, long bankID, String clientID, long accountID) {
        if (accountDAO.existsById(accountID) ) {
            System.out.println("There is already an account with the same id");
        }
        Account account = new Account();
        account.setAccountID(accountID); account.setAccountType(accountType);
        account.setBalance(0.0); account.setClientID(clientID);
        if (accountType.toString().equals("CHECKING") || accountType.toString().equals("SAVING") ) {
            account.setWithdrawAllowed(true);
            accountDAO.save(account);
            System.out.println("The bank account has been created");
        }
        else if (accountType.toString().equals("FIXED")){
            account.setWithdrawAllowed(false);
            accountDAO.save(account);
            System.out.println("The bank account has been created");
        }
        else if (!accountType.toString().equals("CHECKING") && !accountType.toString().equals("SAVING") && !accountType.toString().equals("FIXED")) {
            System.out.println("Bank account has not been created");
        }
    }

    @Override
    public void delete(long accountID) {
        accountDAO.findById(accountID).orElseThrow(() -> new AccountNotFoundException(accountID));
        accountDAO.deleteById(accountID);
    }
}
