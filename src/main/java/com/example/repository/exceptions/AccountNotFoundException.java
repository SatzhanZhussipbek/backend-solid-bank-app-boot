package com.example.repository.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super(String.format("Could not find account %d", id));
    }

}
