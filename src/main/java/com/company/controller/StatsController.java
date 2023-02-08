package com.company.controller;

import com.company.service.OverallStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * посмотреть статистику прохождения "тестирования"
 */

@Slf4j
@RestController
public class StatsController {

    @Autowired
    private OverallStatsService overallStatsService;

    @GetMapping("overall-stats")
    public ResponseEntity<Object> personsCount() {
        return ResponseEntity.ok(overallStatsService.stat());
    }
}
