package com.company.repository;

import com.company.model.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class AnswerDao {

    public static final String GIVE_ANSWER_SQL = "INSERT INTO public.answers(user_value, question_id, person_id, result) VALUES (?, ?, ?, ?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Answer answer) {
        jdbcTemplate.update(GIVE_ANSWER_SQL,
                answer.getUserValue(),
                answer.getQuestionId(),
                answer.getPersonId(),
                answer.getResult());
    }

    public List<Answer> findByUser(long id) {
        return jdbcTemplate.query("SELECT * FROM public.answers WHERE person_id = ?",
                (rs, i) -> toAnswer(rs), id);
    }

    private static Answer toAnswer(ResultSet rs) throws SQLException {
        return Answer.builder()
                .id(rs.getLong("id"))
                .personId(rs.getLong("person_id"))
                .questionId(rs.getLong("question_id"))
                .userValue(rs.getString("user_value"))
                .result(rs.getBoolean("result"))
                .build();
    }
}
