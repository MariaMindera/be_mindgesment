package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.PasswordReset;
import com.mindera.school.mindgesment.services.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/change-password")
@RestController
public class ChangePasswordController {

    private final ChangePasswordService service;

    @Autowired
    public ChangePasswordController(ChangePasswordService service) {
        this.service = service;
    }

    @GetMapping
    public void requestChangePassword(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user) {
        service.requestChangePassword(user.getPrincipal().toString());
    }

    @PostMapping
    public void changePassword(@Valid @RequestBody final PasswordReset passwordReset) {
        service.changePassword(passwordReset);
    }
}
