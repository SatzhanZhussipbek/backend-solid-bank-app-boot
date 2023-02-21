package com.example.repository.repository;

import com.example.repository.model.Account;
import com.example.repository.model.AccountType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDAO extends CrudRepository<Account, String> {
    @Transactional
    @Modifying
    @Query(value = "update Account a set a.balance=?1 where a.account_id=?2",
            nativeQuery = true)
    void updateAccountSetBalance(double amount, long accountId);

    List<Account> getAccountsByClientIDAndAccountType(String clientID, AccountType accountType);
    @Query("select a from Account a where " +
            "a.clientID=:client and a.accountID=:account")
    Account getAccountByClientIDAndAccountID(@Param("client") String clientID,
                                                    @Param("account")long accountID);
    @Query("select a from Account a where a.clientID=?1")
    List<Account> getAccountsByClientID(String clientID);
}
