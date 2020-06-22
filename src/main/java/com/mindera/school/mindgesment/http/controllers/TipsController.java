package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.Tip;
import com.mindera.school.mindgesment.services.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/tips")
public class TipsController {

    private final TipService service;

    @Autowired
    public TipsController(TipService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<Void> create(
            @RequestBody @Valid Tip tip,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        var username = user.getPrincipal().toString();
        service.validateUserAdmin(username);
        service.create(username, tip);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{tip_id}")
    @PreAuthorize("hasRole('ROLE_ADM')")
    public Tip edit(
            @PathVariable(value = "tip_id") final String tipId,
            @RequestBody @Valid Tip tip,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        var username = user.getPrincipal().toString();
        service.validateUserAdmin(username);
        return service.edit(username, tipId, tip);
    }

    @DeleteMapping("/{tip_id}")
    @PreAuthorize("hasRole('ROLE_ADM')")
    public void delete(
            @PathVariable(value = "tip_id") final String tipId,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        var username = user.getPrincipal().toString();
        service.validateUserAdmin(username);
        service.delete(username, tipId);
    }
}
