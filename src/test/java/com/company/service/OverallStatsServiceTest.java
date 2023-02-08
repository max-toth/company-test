package com.company.service;

import com.company.Application;
import com.company.model.OverallStat;
import com.company.repository.AnswerDao;
import com.company.repository.QuestionDao;
import com.company.model.Answer;
import com.company.model.AuthRequest;
import com.company.model.Question;
import com.company.utils.ResourcesUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class OverallStatsServiceTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private OverallStatsService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест общей статистики по тестированию")
    void testOverallStatCalculation() throws Exception {

        // Arrange
        addQuestions();

        // зарегистрировались 10 человек
        addPersons();

        // 1 ответил все 5 вопросов верно
        addSmartPerson();

        // 7 приянли участие ( с учетом одного выше )
        add7Attenders();

        // 4 ответили на все вопросы
        add4AttendersCompleted();


        // Act
        val overallStat = service.stat();


        // Assert
        assertEquals(10, overallStat.getPersonsCount());
        assertEquals(7, overallStat.getAttendersCount());
        assertEquals(4, overallStat.getAttendersCompletedCount());
        assertEquals(1, overallStat.getAttendersFullPassedCount());
    }

    private void add4AttendersCompleted() {
        for (long questionId = 2; questionId < 6; questionId++) {
            for (long id = 2; id < 5; id++) {
                answerDao.save(Answer.builder()
                        .personId(id)
                        .questionId(questionId)
                        .userValue("userValue")
                        .result(RandomUtils.nextBoolean())
                        .build());
            }
        }
    }

    private void add7Attenders() {
        for (long id = 2; id < 8; id++) {
            answerDao.save(Answer.builder()
                    .personId(id)
                    .questionId(1l) // допустим все ответили на первый вопрос
                    .userValue("userValue")
                    .result(RandomUtils.nextBoolean())
                    .build());
        }
    }

    private void addSmartPerson() {
        for (long questionId = 1; questionId < 6; questionId++) {
            answerDao.save(Answer.builder()
                    .personId(1l)
                    .questionId(questionId)
                    .userValue("userValue")
                    .result(true)
                    .build());
        }
    }

    private void addPersons() {
        for (int i = 0; i < 10; i++) {
            authService.register(AuthRequest.builder()
                    .username("user." + i)
                    .password("pass")
                    .passwordConfirm("pass")
                    .build());
        }
    }

    private void addQuestions() throws IOException {
        val questionsJson = ResourcesUtils.readResource("/questions.json");
        List<Question> questions = objectMapper.readValue(questionsJson, new TypeReference<>() {
        });
        questionDao.save(questions);
    }

}