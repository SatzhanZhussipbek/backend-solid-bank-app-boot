package com.example.repository.security;

import com.example.repository.entity.Person;
import com.example.repository.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;
    @Autowired
    public AccountUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.getPersonByClientName(username);
        if (person == null) {
            throw new UsernameNotFoundException("username "+username+" is not found");
        }
        return new AccountUserDetails(person);
    }
}
