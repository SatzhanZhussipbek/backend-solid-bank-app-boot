package com.example.repository;

import com.example.repository.exceptions.ErrorTemplate;
import com.example.repository.presentation.AccountBasicCLI;
import com.example.repository.presentation.MyCLI;
import com.example.repository.presentation.TransactionDepositCLI;
import com.example.repository.presentation.TransactionWithdrawCLI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class BankAppRepositoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankAppRepositoryApplication.class, args);
    }
}
