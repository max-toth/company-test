package com.company.repository;

import com.company.model.Question;
import com.company.model.QuestionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
public class QuestionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(List<Question> data) {
        jdbcTemplate.batchUpdate("INSERT INTO public.questions (question, answers, correct_answer_idx, answer_type) VALUES (?, ?, ?, ?)",
                data, 5,
                (PreparedStatement ps, Question question) -> {
                    ps.setString(1, question.getQuestion());
                    ps.setArray(2, jdbcTemplate.getDataSource().getConnection().createArrayOf("varchar", question.getAnswers()));
                    ps.setInt(3, question.getCorrectAnswerIdx());
                    ps.setString(4, question.getType().name());
                });
    }

    public Question getById(Long questionId) {
        return jdbcTemplate.query("SELECT * FROM public.questions WHERE id = ?",
                        (rs, i) -> {
                            return Question.builder()
                                    .id(rs.getLong("id"))
                                    .type(QuestionType.valueOf(rs.getString("answer_type")))
                                    .question(rs.getString("question"))
                                    .answers(ArrayUtils.toStringArray((Object[]) rs.getArray("answers").getArray()))
                                    .build();
                        }, questionId)
                .stream().findFirst()
                .orElse(null);
    }
}
