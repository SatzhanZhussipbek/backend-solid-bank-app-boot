package com.example.repository.presentation;

import com.example.repository.model.AccountType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyCLI implements CLIUI{
    private Scanner scan;

    private String type;

    public Scanner getScanner() {
        return scan;
    }

    MyCLI() {
        this.scan = new Scanner(System.in);
    }

    public MyCLI(Scanner scanner) {
        this.scan = scanner;
    }

    public double requestClientAmount() {
        System.out.println("Type the amount, please");
            if (!scan.hasNext("[0-9]*")) {
                System.out.println("You typed wrong. Type the number.");
                return 0;
            }
            else {
                return scan.nextDouble();
            }
    }

    public String requestClientAccountNumber() {
        System.out.println("Type the account ID, please");
        return scan.nextLine();
    }

    public AccountType requestAccountType() {
        type = scan.nextLine();
        if (!AccountType.CHECKING.toString().equals(type) && !AccountType.SAVING.toString().equals(type) && !AccountType.FIXED.toString().equals(type)) {
            System.out.println("Bank account was not created");
            return null;
        }
        return AccountType.valueOf(type);
    }

    public void setAccountType(String type) {
        this.type = type;
    }
}

