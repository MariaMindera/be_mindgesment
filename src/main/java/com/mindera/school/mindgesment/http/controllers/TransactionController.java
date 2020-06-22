package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.Transaction;
import com.mindera.school.mindgesment.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/transaction")
@RestController
public class TransactionController {

    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<Transaction> add(
            @Valid @RequestBody final Transaction newTransaction,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        return ResponseEntity
                .status(201)
                .body(service.add(newTransaction, user.getPrincipal().toString()));
    }

    @PutMapping("/{transaction_id}")
    public Transaction edit(
            @PathVariable(value = "transaction_id") final String transactionId,
            @Valid @RequestBody final Transaction editedTransaction,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        return service.edit(transactionId, editedTransaction, user.getPrincipal().toString());
    }

    @DeleteMapping("/{transaction_id}")
    public void delete(
            @PathVariable(value = "transaction_id") final String transactionId,
            @AuthenticationPrincipal final UsernamePasswordAuthenticationToken user
    ) {
        service.delete(transactionId, user.getPrincipal().toString());
    }
}
