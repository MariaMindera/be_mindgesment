package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.PaginatedTransactions;
import com.mindera.school.mindgesment.http.models.PaginatedWishes;

public interface WishGetService {

    PaginatedWishes findAll(String username, Integer page);

    PaginatedTransactions findAllTransactions(String username, String wishId, Integer page);
}
