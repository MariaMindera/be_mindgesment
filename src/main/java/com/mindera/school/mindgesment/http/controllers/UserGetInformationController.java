package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.User;
import com.mindera.school.mindgesment.services.UserGetInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserGetInformationController {

    private final UserGetInformationService service;

    @Autowired
    public UserGetInformationController(UserGetInformationService service) {
        this.service = service;
    }

    @GetMapping("")
    public User getUser(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user) {
        return service.getUser(user.getPrincipal().toString());
    }

    @GetMapping("/balance")
    public Map<String, Double> getUserBalance(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user) {
        return service.getUserBalance(user.getPrincipal().toString());
    }

    @GetMapping("/balance/all")
    public Map<String, Double> getUserAllBalance(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user) {
        return service.getUserAllBalance(user.getPrincipal().toString());
    }
}
