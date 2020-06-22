package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.PasswordForgot;
import com.mindera.school.mindgesment.services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/forgot-password")
@RestController
public class ForgotPasswordController {

    private final ForgotPasswordService service;

    @Autowired
    public ForgotPasswordController(ForgotPasswordService service) {
        this.service = service;
    }

    @PostMapping("")
    public void processForgotPassword(@Valid @RequestBody PasswordForgot passwordForgot) {
        service.processForgotPassword(passwordForgot);
    }
}
