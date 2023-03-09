package com.example.repository.service;

import com.example.repository.model.AccountType;
import com.example.repository.model.JwtRequest;
import com.example.repository.model.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface UserCreationService {

    ResponseEntity<Object> register(String name, String password);

    JwtResponse authenticateUser(String name, String password) throws Exception;

}
