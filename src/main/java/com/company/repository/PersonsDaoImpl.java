package com.company.repository;

import com.company.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
public class PersonsDaoImpl implements PersonsDao {

    public static final String CREATE_NEW_USER_SQL = "INSERT INTO public.persons (username, pwd) VALUES (?, ?)";
    public static final String GET_BY_USERNAME_SQL = "SELECT * FROM public.persons WHERE username = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Person person) {
        jdbcTemplate.update(CREATE_NEW_USER_SQL, person.getUsername(), person.getPassword());
    }

    @Override
    public Person getByUsername(String username) {
        return jdbcTemplate.query(GET_BY_USERNAME_SQL,
                        (resultSet, i) -> toPerson(resultSet), username).stream()
                .findFirst()
                .orElse(null);
    }

    private static Person toPerson(ResultSet resultSet) throws SQLException {
        return Person.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("pwd"))
                .build();
    }
}
