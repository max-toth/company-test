package com.company.controller;

import com.company.model.Person;
import com.company.service.ProfileStatsService;
import com.company.utils.SessionHolderEmulator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * посмотреть статистику по текущему пользователю
 */

@Slf4j
@RestController
public class ProfileStatsController {

    @Autowired
    private ProfileStatsService profileStatsService;

    @GetMapping("profile-stats")
    public ResponseEntity<Object> personProgress(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Person person = SessionHolderEmulator.get(token);
        if (Objects.isNull(person)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(profileStatsService.stat(person));
    }
}
