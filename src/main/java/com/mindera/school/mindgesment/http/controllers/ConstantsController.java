package com.mindera.school.mindgesment.http.controllers;

import com.mindera.school.mindgesment.http.models.CategoryExpense;
import com.mindera.school.mindgesment.http.models.CategoryIncome;
import com.mindera.school.mindgesment.http.models.Coin;
import com.mindera.school.mindgesment.http.models.TransactionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/constants")
public class ConstantsController {

    @GetMapping("/categories")
    public Map<String, List<?>> getCategoriesIncome() {
        var response = new HashMap<String, List<?>>();
        response.put(
                "income",
                Arrays.stream(CategoryIncome.values())
                        .collect(Collectors.toList()));
        response.put(
                "expense",
                Arrays.stream(CategoryExpense.values())
                        .collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/coins")
    public List<Coin> getCoins() {
        return Arrays.stream(Coin.values())
                .collect(Collectors.toList());
    }

    @GetMapping("/transactionTypes")
    public List<TransactionType> getTransactionType() {
        return Arrays.stream(TransactionType.values())
                .collect(Collectors.toList());
    }
}
