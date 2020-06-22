package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.http.models.PaginatedTips;
import com.mindera.school.mindgesment.services.TipGetService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tips")
public class TipsGetController {

    private final TipGetService service;

    @Autowired
    public TipsGetController(TipGetService service) {
        this.service = service;
    }

    @GetMapping("")
    public PaginatedTips getAll(
            @RequestParam @NotNull Integer page
    ) {
        if (page < 0) {
            throw new InvalidParameter("page", "greater than or equal to 0");
        }

        return service.getAll(page);
    }
}
