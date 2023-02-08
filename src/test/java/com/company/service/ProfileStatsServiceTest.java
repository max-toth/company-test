package com.company.service;

import com.company.Application;
import com.company.model.Answer;
import com.company.model.AuthRequest;
import com.company.model.Person;
import com.company.model.ProfileStat;
import com.company.model.Question;
import com.company.repository.AnswerDao;
import com.company.repository.QuestionDao;
import com.company.utils.ResourcesUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class ProfileStatsServiceTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private ProfileStatsService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест профильной статистики по тестированию")
    void testProfileStatCalculation() throws Exception {

        // Arrange
        Person current = Person.builder()
                .id(1)
                .build();
        addQuestions();

        // 100 пользователей зарегистрировалось
        // из них 50 + 1 приняли участие и завершили тестирование
        int attenders = 51;
        addPersons(100);

        // 30 из них ответили правильно на 2 вопроса
        int worsePercents = 100 / attenders * 30;
        add30UsersWith2CorrectAnswers();

        // 20 из ни ответили правильно на 4 вопроса
        int betterPercents = 100 / attenders * 20;
        add20UsersWith4CorrectAnswers();

        // я ответил правильно на 3 вопроса
        int currentProgress = 100 / 5 * 3;
        currentAnswered3Correct();

        // Act
        ProfileStat profileStat = service.stat(current);

        // Assert
        assertEquals(currentProgress, profileStat.getProgress());
        assertEquals(worsePercents, profileStat.getWorseThanMe());
        assertEquals(betterPercents, profileStat.getBetterThanMe());
    }

    private void currentAnswered3Correct() {
        for (long questionId = 1; questionId < 4; questionId++) {
            answerDao.save(Answer.builder()
                    .personId(1l)
                    .questionId(questionId)
                    .userValue("userValue")
                    .result(true)
                    .build());
        }
        for (long questionId = 4; questionId < 6; questionId++) {
            answerDao.save(Answer.builder()
                    .personId(1l)
                    .questionId(questionId)
                    .userValue("userValue")
                    .result(false)
                    .build());
        }
    }

    private void add20UsersWith4CorrectAnswers() {
        for (long questionId = 1; questionId < 5; questionId++) {
            for (long userId = 32; userId < 52; userId++) {
                answerDao.save(Answer.builder()
                        .personId(userId)
                        .questionId(questionId)
                        .userValue("userValue")
                        .result(true)
                        .build());
            }
        }
        for (long questionId = 5; questionId < 6; questionId++) {
            for (long userId = 32; userId < 52; userId++) {
                answerDao.save(Answer.builder()
                        .personId(userId)
                        .questionId(questionId)
                        .userValue("userValue")
                        .result(false)
                        .build());
            }
        }
    }

    private void add30UsersWith2CorrectAnswers() {
        for (long questionId = 1; questionId < 3; questionId++) {
            for (long userId = 2; userId < 32; userId++) {
                answerDao.save(Answer.builder()
                        .personId(userId)
                        .questionId(questionId)
                        .userValue("userValue")
                        .result(true)
                        .build());
            }
        }
        for (long questionId = 3; questionId < 6; questionId++) {
            for (long userId = 2; userId < 32; userId++) {
                answerDao.save(Answer.builder()
                        .personId(userId)
                        .questionId(questionId)
                        .userValue("userValue")
                        .result(false)
                        .build());
            }
        }
    }

    private void addPersons(int n) {
        for (int i = 0; i < n; i++) {
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