package com.example.repository.repository;

import com.example.repository.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
        @Query("select t from Transaction t where t.accountID = ?1")
        List<Transaction> getTransactionsByAccountID(long accountID);
}

