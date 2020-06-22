package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.PaginatedTransactions;
import com.mindera.school.mindgesment.services.TransactionGetService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.DateTimeException;
import java.time.LocalDate;

@RequestMapping("/transaction")
@RestController
public class TransactionGetController {

    private final TransactionGetService service;

    @Autowired
    public TransactionGetController(TransactionGetService service) {
        this.service = service;
    }

    @GetMapping("")
    public PaginatedTransactions findAllByUser(
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        return service.findAllByUser(user.getPrincipal().toString(), page);
    }

    @GetMapping("/category")
    public PaginatedTransactions findAllByCategory(
            @RequestParam @NotBlank String category,
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        return service.findAllByCategory(user.getPrincipal().toString(), category, page);
    }

    @GetMapping("/type")
    public PaginatedTransactions findAllByType(
            @RequestParam @NotBlank String type,
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        return service.findAllByType(user.getPrincipal().toString(), type, page);
    }

    @GetMapping("/date")
    public PaginatedTransactions findAllByDate(
            @RequestParam @NotBlank String date,
            @RequestParam @NotNull Integer page,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        validatePage(page);
        var dateConverted = LocalDate.parse(date);

        if (dateConverted.isAfter(LocalDate.now())) {
            throw new DateTimeException("Date must be past or present");
        }

        return service.findAllByDate(user.getPrincipal().toString(), dateConverted, page);
    }

    private void validatePage(Integer page) {
        if (page < 0) {
            throw new InvalidParameter("page", "greater than or equal to 0");
        }
    }
}
