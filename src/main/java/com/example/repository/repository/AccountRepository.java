package com.example.repository.repository;

import com.example.repository.entity.Account;
import com.example.repository.model.AccountType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Transactional
    @Modifying
    @Query(value = "update Account a set a.balance=?1 where a.account_id=?2",
            nativeQuery = true)
    void updateAccountSetBalance(double amount, long accountId);

    List<Account> getAccountsByClientIDAndAccountType(long clientID, AccountType accountType);
    @Query("select a from Account a where " +
            "a.clientID=:client and a.accountID=:account")
    Account getAccountByClientIDAndAccountID(@Param("client") long clientID,
                                                    @Param("account")long accountID);
    @Query("select a from Account a where a.clientID=?1")
    List<Account> getAccountsByClientID(long clientID);
    @Query("select a from Account a where a.accountID=?1")
    Account getAccountByAccountID(long accountID);
}
