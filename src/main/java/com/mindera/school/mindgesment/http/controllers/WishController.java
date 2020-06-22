package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/wish")
@RestController
public class WishController {

    private final WishService service;

    @Autowired
    public WishController(WishService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public Map<String, Long> getBalance(
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        var response = new HashMap<String, Long>();
        response.put(
                "balance",
                Objects.requireNonNullElse(service.getBalance(user.getPrincipal().toString()), 0L));
        return response;
    }

    @GetMapping("/proposalAmount")
    public Map<String, Double> getProposalAmount(
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        var response = new HashMap<String, Double>();
        response.put(
                "proposalAmount",
                service.getProposalAmount(user.getPrincipal().toString()));
        return response;
    }

    @PostMapping("/balance/{wish_id}")
    public void addBalanceWish(
            @PathVariable(value = "wish_id") final String wishId,
            @RequestParam final Long balance,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        if (balance <= 0) {
            throw new InvalidParameter("balance", "greater than 0");
        }
        service.addBalanceWish(user.getPrincipal().toString(), wishId, balance);
    }

    @PutMapping("/{wish_id}")
    public Wish editWish(
            @PathVariable(value = "wish_id") final String wishId,
            @Valid @RequestBody final Wish editedWish,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        return service.editWish(user.getPrincipal().toString(), wishId, editedWish);
    }

    @DeleteMapping("/{wish_id}")
    public void deleteWish(
            @PathVariable(value = "wish_id") final String wishId,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        service.deleteWish(user.getPrincipal().toString(), wishId);
    }

    @PostMapping("/complete/{wish_id}")
    public void completeWish(
            @PathVariable(value = "wish_id") final String wishId,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        service.completeWish(user.getPrincipal().toString(), wishId);
    }
}
