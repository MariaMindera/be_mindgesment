package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.UserRegister;
import com.mindera.school.mindgesment.services.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRegisterController {

    private final UserRegisterService service;

    @Autowired
    public UserRegisterController(UserRegisterService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody final UserRegister user) {
        service.register(user);
        return ResponseEntity.status(201).build();
    }
}
