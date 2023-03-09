package com.example.repository.service;

import com.example.repository.entity.Account;
import com.example.repository.entity.Person;
import com.example.repository.exceptions.ErrorTemplate;
import com.example.repository.model.JwtRequest;
import com.example.repository.model.JwtResponse;
import com.example.repository.repository.PersonRepository;
import com.example.repository.security.AccountUserDetails;
import com.example.repository.util.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserCreationServiceImpl implements UserCreationService{

    private PersonRepository personRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;
    @Autowired
    public UserCreationServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder,
                                   JwtService jwtService, AuthenticationManager authenticationManager) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public ResponseEntity<Object> register(String name, String password) {
        if (personRepository.getPersonByClientName(name) != null) {
            System.out.println("There is already a user with such a name.");
        }
        Person person = new Person(name, passwordEncoder.encode(password));
        //AccountUserDetails userAccount = new AccountUserDetails(person);
        personRepository.save(person);
        //var jwtToken = jwtService.generateToken(userAccount);
        return new ResponseEntity<>(new ErrorTemplate(String.format("User %d created", person.getClientID())),
                HttpStatus.OK);
    }

    @Override
    public JwtResponse authenticateUser(String name1, String password1) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    name1, password1));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception(e.getMessage()+" "+e.getLocalizedMessage());
        }
        Person user = personRepository.getPersonByClientName(
                name1);
        if (user == null) {
            throw new UsernameNotFoundException(name1);
        }
        AccountUserDetails accountUserDetails = new AccountUserDetails(user);
        String jwtToken = jwtService.generateToken(accountUserDetails);
        return new JwtResponse(jwtToken);
    }


}
