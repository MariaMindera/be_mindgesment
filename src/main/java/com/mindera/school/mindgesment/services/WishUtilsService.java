package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.entities.WishEntity;

import java.time.YearMonth;
import java.util.Map;

public interface WishUtilsService {

    WishEntity findWishEntity(String wishId);

    UserEntity findUserEntity(String username);

    Double getProposalAmount(Double totalIncome, Double totalExpense);

    Map<String, Double> getTotalIncomeAndExpenseByUser(UserEntity user);

    YearMonth calculateData(double total, double balance, double proposalAmount);

    Long getBalanceWish(WishEntity wish);
}
