package com.example.repository.service;

import com.example.repository.exceptions.AccountNotFoundException;
import com.example.repository.model.Transaction;
import com.example.repository.repository.AccountDAO;
import com.example.repository.model.Account;
import com.example.repository.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountWithdrawServiceImpl implements AccountWithdrawService{
    private AccountDAO accountDAO;

    private TransactionRepository transactionRepository;
    @Autowired
    public AccountWithdrawServiceImpl(AccountDAO accountDAO, TransactionRepository transactionRepository) {
        this.accountDAO = accountDAO;
        this.transactionRepository = transactionRepository;
    }
    @Override
    public void withdraw(double amount, Account account) {
        if (accountDAO.getAccountByClientIDAndAccountID(account.getClientID(), account.getAccountID()) != null) {
            accountDAO.updateAccountSetBalance(account.getBalance() - amount, account.getAccountID());
            Transaction newTransaction = new Transaction(account.getClientID(),
                    account.getAccountID(), account.getAccountType(), amount);
            transactionRepository.save(newTransaction);
        }
        else {
            throw new AccountNotFoundException(account.getAccountID());
        }
    }
}
