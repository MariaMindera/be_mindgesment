package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.Wish;

public interface WishService {

    Long getBalance(String username);

    Double getProposalAmount(String username);

    void addBalanceWish(String username, String wishId, Long balance);

    Wish editWish(String username, String wishId, Wish wish);

    void deleteWish(String username, String wishId);

    void completeWish(String username, String wishId);

    void delete(String wishId);
}
