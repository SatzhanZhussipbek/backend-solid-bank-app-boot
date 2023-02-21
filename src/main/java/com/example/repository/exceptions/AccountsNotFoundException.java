package com.example.repository.exceptions;

public class AccountsNotFoundException extends RuntimeException{
    public AccountsNotFoundException(Long id) {
        super(String.format("Could not find accounts under this client id: %d", id));
    }
}
