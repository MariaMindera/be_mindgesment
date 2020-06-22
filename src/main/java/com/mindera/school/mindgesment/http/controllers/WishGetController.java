package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.PaginatedTransactions;
import com.mindera.school.mindgesment.http.models.PaginatedWishes;
import com.mindera.school.mindgesment.services.WishGetService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/wish")
@RestController
public class WishGetController {

    private final WishGetService service;

    @Autowired
    public WishGetController(WishGetService service) {
        this.service = service;
    }

    @GetMapping("")
    public PaginatedWishes findAll(
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        return service.findAll(user.getPrincipal().toString(), page);
    }

    @GetMapping("/transaction/{wish_id}")
    public PaginatedTransactions findAllTransactions(
            @PathVariable(value = "wish_id") final String wishId,
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        return service.findAllTransactions(user.getPrincipal().toString(), wishId, page);
    }

    private void validatePage(Integer page) {
        if (page < 0) {
            throw new InvalidParameter("page", "greater than or equal to 0");
        }
    }
}
