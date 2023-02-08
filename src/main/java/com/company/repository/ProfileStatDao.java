package com.company.repository;

import com.company.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ProfileStatDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OverallStatsDao overallStatsDao;

    @Autowired
    private QuestionDao questionDao;

    /**
     * процент прохождения тестирования текущего пользователя (сколько правильных ответов он дал)
     * @param person
     * @return
     */
    public Integer progress(Person person) {

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM public.answers WHERE result = true AND person_id = ?",
                Integer.class,
                person.getId()
        );
    }

    /**
     * сколько процентов людей справилось с тестированием хуже текущего пользователя
     * @param person
     * @return
     */
    public Integer worseThanMe(Person person) {

        Integer currentUserPercent = progress(person);

        Integer othersCnt = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM (SELECT COUNT(*) as cnt FROM public.answers WHERE result = true AND person_id <> ? GROUP BY person_id HAVING cnt < ?)",
                Integer.class,
                person.getId(),
                currentUserPercent);

        Long attendersCompletedCount = overallStatsDao.attendersCompletedCount();

        return Math.toIntExact(100 / attendersCompletedCount * othersCnt);
    }

    /**
     * сколько процентов людей справилось с тестированием лучше текущего пользователя
     * @param person
     * @return
     */
    public Integer betterThanMe(Person person) {
        Integer currentUserPercent = progress(person);

        Integer othersCnt = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM (SELECT COUNT(*) as cnt FROM public.answers WHERE result = true AND person_id <> ? GROUP BY person_id HAVING cnt > ?)",
                Integer.class,
                person.getId(),
                currentUserPercent);

        Long attendersCompletedCount = overallStatsDao.attendersCompletedCount();

        return Math.toIntExact(100 / attendersCompletedCount * othersCnt);
    }
}
