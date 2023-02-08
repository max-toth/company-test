package com.company.controller;

import com.company.model.AuthRequest;
import com.company.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest request) {
        log.info("SignUp REST: {}", request);
        authService.register(request);
        return ResponseEntity.ok("Successful signup");
    }

    @PostMapping("signin")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        log.info("SignIn REST: {}", request);
        return ResponseEntity.ok(authService.auth(request));
    }

}
