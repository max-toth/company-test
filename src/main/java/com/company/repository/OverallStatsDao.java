package com.company.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OverallStatsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long personsCount() {
        // сколько всего пользователей зарегистрировано в системе
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM public.persons", Long.class);
    }

    public Long attendersCount() {
        // сколько пользователей прошли тестирование
        return jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT(person_id)) FROM public.answers", Long.class);
    }

    public Long attendersCompletedCount() {
        // сколько пользователей ответили на все вопросы тестирования
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM (SELECT COUNT(person_id) as answers_cnt FROM public.answers GROUP BY person_id HAVING answers_cnt = 5)", Long.class);
    }

    public Long attendersFullPassedCount() {
        // сколько пользователей ответили на все вопросы тестирования правильно.
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM (SELECT COUNT(*) as attenders FROM public.answers WHERE result = true GROUP BY person_id HAVING attenders = 5)", Long.class);
    }
}