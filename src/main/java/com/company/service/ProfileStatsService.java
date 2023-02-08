package com.company.service;

import com.company.model.Person;
import com.company.model.ProfileStat;
import com.company.repository.ProfileStatDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileStatsService {

    @Autowired
    private ProfileStatDao profileStatDao;

    public ProfileStat stat(Person person) {
        int questionCount = 5; // questionDao.questionCount()

        return ProfileStat.builder()
                .progress(100 / questionCount * profileStatDao.progress(person))
                .worseThanMe(profileStatDao.worseThanMe(person))
                .betterThanMe(profileStatDao.betterThanMe(person))
                .build();
    }
}

/*
1. процент прохождения тестирования текущего пользователя (сколько правильных ответов он дал)

    100 / 5 * ()

2. сколько процентов людей справилось с тестированием хуже текущего пользователя
     - взять процент текущего
     - получить проценты остальных
     - отфильтровать те что меньше
     - посчитать процент от общего числа участников

     currentUserPercent = 100 / 5 * (SELECT COUNT(*) FROM public.answers WHERE result = true AND person_id = ?)

     others = SELECT COUNT(*) as cnt FROM public.answers WHERE result = true AND person_id <> ? GROUP BY person_id HAVING 100 / 5 * cnt < currentUserPercent

     100 / 10 * others

3. сколько процентов людей справилось с тестированием лучше текущего пользователя.

    others = ... HAVING 100 / 5 * cnt > currentUserPercent

    100 / 10 * others
 */