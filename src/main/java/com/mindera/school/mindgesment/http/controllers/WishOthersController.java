package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.OthersWish;
import com.mindera.school.mindgesment.services.WishOthersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/wish/others")
@RestController
public class WishOthersController {

    private final WishOthersService service;

    @Autowired
    public WishOthersController(WishOthersService service) {
        this.service = service;
    }

    @GetMapping("/proposalAmount")
    public Map<String, Object> getProposalAmount(
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        return service.getProposalAmount(user.getPrincipal().toString());
    }

    @PostMapping("")
    public ResponseEntity<Void> addWish(
            @Valid @RequestBody final OthersWish othersWishes,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        service.addWish(user.getPrincipal().toString(), othersWishes.getWish(), othersWishes.getOthersWishes());
        return ResponseEntity.status(201).build();
    }
}
