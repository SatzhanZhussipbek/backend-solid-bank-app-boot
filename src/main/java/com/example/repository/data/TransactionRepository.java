package com.example.repository.data;

import com.example.repository.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
        /*@Query(value = "select t from Transaction t")
        List<Transaction> getTransactions();*/
}

