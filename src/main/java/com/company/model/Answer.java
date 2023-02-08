package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {

    private Long id;

    private Long questionId;

    private Long personId;

    private String userValue;

    private Boolean result;

}
