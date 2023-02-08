package com.company.service;

import com.company.model.AuthRequest;
import com.company.model.Person;
import com.company.repository.PersonsDao;
import com.company.utils.SessionHolderEmulator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private PersonsDao personsDao;

    @Transactional
    public String auth(AuthRequest request) {

        Person person = personsDao.getByUsername(request.getUsername());

        if (Objects.isNull(person)) {
            throw new RuntimeException("No such user registered...");
        }

        if (StringUtils.equals(person.getPassword(), request.getPassword())) {
            String sessionToken = UUID.randomUUID().toString();

            SessionHolderEmulator.create(sessionToken, person);

            return sessionToken;
        } else {
            throw new RuntimeException("Wrong credentials...");
        }
    }

    @Transactional
    public void register(AuthRequest request) {

        // Sort of validation before...
        if (StringUtils.isEmpty(request.getUsername())
                || StringUtils.isEmpty(request.getPassword())
                || StringUtils.isEmpty(request.getPasswordConfirm())
        ) {
            throw new RuntimeException("Wrong data for signup");
        }

        if (!StringUtils.equals(request.getPassword(), request.getPasswordConfirm())) {
            throw new RuntimeException("Passwords does not match");
        }

        personsDao.save(Person.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
    }
}
