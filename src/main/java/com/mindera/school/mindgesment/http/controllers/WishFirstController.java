package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/wish/first")
@RestController
public class WishFirstController {

    private final WishFirstService service;

    @Autowired
    public WishFirstController(WishFirstService service) {
        this.service = service;
    }

    @GetMapping("/proposalAmount")
    public Map<String, Object> getProposalAmount(
            @RequestParam @NotNull final Long totalAmount,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        if (totalAmount <= 0) {
            throw new InvalidParameter("totalAmount", "greater than 0");
        }
        return service.getProposalAmount(user.getPrincipal().toString(), totalAmount);
    }

    @PostMapping("")
    public ResponseEntity<Wish> addNewWish(
            @Valid @RequestBody final Wish wish,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        return ResponseEntity
                .status(201)
                .body(service.addNewWish(user.getPrincipal().toString(), wish));
    }
}
