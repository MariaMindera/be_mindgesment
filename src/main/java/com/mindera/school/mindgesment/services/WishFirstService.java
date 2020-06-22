package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.Wish;

import java.util.Map;

public interface WishFirstService {

    Map<String, Object> getProposalAmount(String username, Long totalAmount);

    Wish addNewWish(String username, Wish wish);
}
