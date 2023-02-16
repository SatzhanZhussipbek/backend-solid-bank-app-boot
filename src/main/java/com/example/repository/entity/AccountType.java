package com.example.repository.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public enum AccountType {
    CHECKING,
    SAVING,
    FIXED
}
