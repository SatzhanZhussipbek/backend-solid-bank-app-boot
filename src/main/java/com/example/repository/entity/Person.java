package com.example.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="PERSON")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private long clientID;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "client_password")
    private String password;

    public Person(String clientName, String password) {
        this.clientName = clientName;
        this.password = password;
    }

}
