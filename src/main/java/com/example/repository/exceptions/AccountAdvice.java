package com.example.repository.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccountAdvice {
    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountNotFound(AccountNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccountsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String accountsNotFound(AccountsNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TransactionsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String transactionsNotFound(TransactionsNotFoundException ex) {
        return ex.getMessage();
    }

}
