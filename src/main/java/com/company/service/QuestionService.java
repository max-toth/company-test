package com.company.service;

import com.company.repository.QuestionDao;
import com.company.model.Question;
import com.company.model.QuestionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public void create(List<Question> data) {

        // Вопросы могут быть 2х типов: <--- Покрыто энумом QuestionType
        //  со свободным вводом ответа,
        //  с выбором ответа из 4х вариантов.
        data.forEach(q -> {
            if (QuestionType.FREE == q.getType() && q.getAnswers().length > 1) {
                throw new RuntimeException("Вопрос со свободным вводом ответа не может содержать множественный выбор");
            }

            if (QuestionType.CHOICE == q.getType() && q.getAnswers().length != 4) {
                throw new RuntimeException("Вопрос с выбором ответа предполагает набор из 4х вариантов");
            }
        });

        // Количество вопросов - 5 штук.
        if (data.size() != 5) {
            throw new RuntimeException("Количество вопросов - 5 штук");
        }

        questionDao.save(data);
    }
}
