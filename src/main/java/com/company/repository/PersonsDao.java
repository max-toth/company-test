package com.company.repository;

import com.company.model.Person;

public interface PersonsDao {
    void save(Person person);

    Person getByUsername(String username);
}
