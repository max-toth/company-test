package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private long id;

    private String username;

    @JsonIgnore
    private String password;
}
