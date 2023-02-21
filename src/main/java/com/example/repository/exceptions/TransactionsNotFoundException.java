package com.example.repository.exceptions;

public class TransactionsNotFoundException extends RuntimeException{
    public TransactionsNotFoundException(Long id) {
        super(String.format("Could not find transactions under this id: %d", id));
    }
}
