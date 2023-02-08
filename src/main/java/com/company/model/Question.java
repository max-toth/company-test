package com.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {

    private Long id;

    @JsonProperty("q")
    private String question;

    @JsonProperty("a")
    private String[] answers;

    @JsonProperty("index")
    private int correctAnswerIdx;

    private QuestionType type;

}
