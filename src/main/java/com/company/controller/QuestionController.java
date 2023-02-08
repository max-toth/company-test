package com.company.controller;

import com.company.service.QuestionService;
import com.company.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("questions")
    public ResponseEntity<Object> create(@RequestBody List<Question> data) {
        log.info("Create Question REST: {}", data);
        questionService.create(data);
        return ResponseEntity.ok("Create questions");
    }
}
