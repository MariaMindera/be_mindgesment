package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.PaginatedTransactions;

import java.time.LocalDate;

public interface TransactionGetService {

    PaginatedTransactions findAllByUser(String username, Integer page);

    PaginatedTransactions findAllByCategory(String username, String category, Integer page);

    PaginatedTransactions findAllByType(String username, String type, Integer page);

    PaginatedTransactions findAllByDate(String username, LocalDate date, Integer page);
}
