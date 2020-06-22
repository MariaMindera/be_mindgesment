package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.User;
import com.mindera.school.mindgesment.services.UserChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;

@RequestMapping("/user")
@RestController
public class UserChangeController {

    private final UserChangeService service;

    @Autowired
    public UserChangeController(UserChangeService service) {
        this.service = service;
    }

    @PatchMapping("/username")
    public User changeUsername(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user,
                               @RequestParam String username
    ) {
        return service.changeUsername(user.getPrincipal().toString(), username);
    }

    @PatchMapping("/coin")
    public User changeCoin(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user,
                           @RequestParam String coin
    ) {
        return service.changeCoin(user.getPrincipal().toString(), coin);
    }

    @PatchMapping("/earnings")
    public User changeEarnings(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user,
                               @RequestParam Double earnings
    ) {
        if (earnings < 0) {
            throw new InvalidParameter("earnings", "greater than or equal to 0");
        }
        return service.changeEarnings(user.getPrincipal().toString(), earnings);
    }

    @PatchMapping("/expenses")
    public User changeExpenses(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user,
                               @RequestParam @DecimalMin("0.00") Double expenses
    ) {
        if (expenses < 0) {
            throw new InvalidParameter("expenses", "greater than or equal to 0");
        }
        return service.changeExpenses(user.getPrincipal().toString(), expenses);
    }

    @PatchMapping("/monthlyPeriod")
    public User changeMonthlyPeriod(@AuthenticationPrincipal final UsernamePasswordAuthenticationToken user,
                                    @RequestParam Integer monthlyPeriod
    ) {
        if (monthlyPeriod < 1 || monthlyPeriod > 31) {
            throw new InvalidParameter("monthly period", "a number between 1 and 31");
        }
        return service.changeMonthlyPeriod(user.getPrincipal().toString(), monthlyPeriod);
    }
}
