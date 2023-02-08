package com.company.controller;

import com.company.model.Answer;
import com.company.model.Person;
import com.company.service.AnswerService;
import com.company.utils.SessionHolderEmulator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("answer")
    public ResponseEntity<Object> answer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @RequestBody Answer answer) {
        log.info("Answer Question REST: {}", answer);

        Person person = SessionHolderEmulator.get(token);
        if (Objects.isNull(person)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        answerService.give(answer, person);
        return ResponseEntity.ok("Answer given");
    }

    @GetMapping("snapshot")
    public ResponseEntity<Object> snapshot(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("Get Snapshot REST: jwt = {}", token);

        Person person = SessionHolderEmulator.get(token);
        if (Objects.isNull(person)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // посмотреть только свои ответы
        return ResponseEntity.ok(answerService.snapshot(person));
    }

}
