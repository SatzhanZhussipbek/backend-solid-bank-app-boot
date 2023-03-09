package com.example.repository;
import com.example.repository.presentation.AccountBasicCLI;
import com.example.repository.presentation.MyCLI;
import com.example.repository.presentation.TransactionDepositCLI;
import com.example.repository.presentation.TransactionWithdrawCLI;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@SecurityScheme(name = "basicauth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, bearerFormat = "JWT")
public class BankAppRepositoryApplication implements CommandLineRunner {
    @Autowired
    private ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(BankAppRepositoryApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Override
    public void run(String... args) throws Exception {
        {
            boolean running = true;
            String clientID = "1";

            MyCLI myCLI = context.getBean(MyCLI.class);
            AccountBasicCLI accountBasicCLI = context.getBean(AccountBasicCLI.class);
            TransactionDepositCLI transactionDepositCLI = context.getBean(TransactionDepositCLI.class);
            TransactionWithdrawCLI transactionWithdrawCLI = context.getBean(TransactionWithdrawCLI.class);

            String helpMessage = "1 - show accounts\n2 - create account\n3 - deposit\n4 - withdraw\n5 - transfer\n6 - this message\n7 - exit\n";
            System.out.printf("Welcome to CLI bank service\n");
            System.out.printf("Enter operation number: \n");
            System.out.printf(helpMessage);
            while (running) {
                switch (myCLI.getScanner().nextLine()) {

                    case "1":
                        accountBasicCLI.getAccounts(Long.parseLong(clientID));
                        break;

                    case "2":
                        System.out.println("Choose the account type you would like to have");
                        System.out.println("[Checking, Saving, Fixed]");
                        accountBasicCLI.createAccountRequest(Long.parseLong(clientID));
                        break;

                    case "3":
                        transactionDepositCLI.depositMoney(Long.parseLong(clientID));
                        break;

                    case "4":
                        transactionWithdrawCLI.withdrawMoney(Long.parseLong(clientID));
                        break;

                    case "5":

                        break;

                    case "6":
                        System.out.printf(helpMessage);
                        break;

                    case "7":
                        System.out.printf("The Application is closed now\n");
                        running = false;
                        break;

                    default:
                        System.out.printf("Command not recognized!\n");
                        break;
                }
            }
            myCLI.getScanner().close();
        }

    }
}
