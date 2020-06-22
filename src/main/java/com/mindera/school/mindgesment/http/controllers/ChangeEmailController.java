package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.EmailChange;
import com.mindera.school.mindgesment.services.ChangeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ChangeEmailController {

    private final ChangeEmailService service;

    @Autowired
    public ChangeEmailController(ChangeEmailService service) {
        this.service = service;
    }

    @GetMapping("/change-email")
    public void requestChangeEmail(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user){
        service.requestChangeEmail(user.getPrincipal().toString());
    }

    @PostMapping("/change-email")
    public void changeEmail(@Valid @RequestBody EmailChange emailChange) {
        service.changeEmail(emailChange);
    }

    @GetMapping("/confirm-email")
    public void confirmChangeEmail(@RequestParam("token") String token) {
        service.confirmChangeEmail(token);
    }
}
