package com.company.service;

import com.company.repository.AnswerDao;
import com.company.repository.QuestionDao;
import com.company.model.Answer;
import com.company.model.Person;
import com.company.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionDao questionDao;

    public void give(Answer answer, Person person) {

        Question question = questionDao.getById(answer.getQuestionId());
        String correctAnswer = question.getAnswers()[question.getCorrectAnswerIdx()];
        
        answer.setResult(StringUtils.equals(answer.getUserValue(), correctAnswer));
        answer.setPersonId(person.getId());

        answerDao.save(answer);
    }

    public List<Answer> snapshot(Person person) {
        return answerDao.findByUser(person.getId());
    }
}
