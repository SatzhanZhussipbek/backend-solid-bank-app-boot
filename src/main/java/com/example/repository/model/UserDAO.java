package com.example.repository.model;

import com.example.repository.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDAO {

    private long id;

    private String username;

    public UserDAO(Person person) {
        this.id = person.getClientID();
        this.username = person.getClientName();
    }
}
