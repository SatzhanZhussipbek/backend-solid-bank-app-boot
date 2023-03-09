package com.example.repository.repository;

import com.example.repository.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    @Query("select p from Person p where p.clientName=?1")
    Person getPersonByClientName(String userName);

    @Query("select p from Person p where p.clientID=?1")
    Person getPersonByClientID(long id);
}
