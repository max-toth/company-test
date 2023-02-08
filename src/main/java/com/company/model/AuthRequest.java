package com.company.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {

    private String username;

    private String password;

    private String passwordConfirm;
}
