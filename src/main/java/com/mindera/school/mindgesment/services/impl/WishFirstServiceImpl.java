package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.AddError;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishFirstService;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WishFirstServiceImpl implements WishFirstService {

    private final WishUtilsService wishUtils;

    private final WishRepository wishRepository;

    private final MapperFacade mapper;

    @Autowired
    public WishFirstServiceImpl(WishUtilsService wishUtils, WishRepository wishRepository, MapperFacade mapper) {
        this.wishUtils = wishUtils;
        this.wishRepository = wishRepository;
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> getProposalAmount(String username, Long totalAmount) {
        var incomeExpense = wishUtils.getTotalIncomeAndExpenseByUser(wishUtils.findUserEntity(username));
        var proposalAmount = wishUtils.getProposalAmount(incomeExpense.get("totalIncome"), incomeExpense.get("totalExpense"));

        var proposalWish = new HashMap<String, Object>();
        proposalWish.put("proposalAmount", Math.round(proposalAmount));
        proposalWish.put("date", wishUtils.calculateData((double) totalAmount, 0.00, proposalAmount));
        return proposalWish;
    }

    @Override
    public Wish addNewWish(String username, Wish wish) {
        if (wish.getProposalAmount() > wish.getTotal()) {
            throw new AddError("Wish", new Throwable("The proposal amount must be less than total."));
        }

        var user = wishUtils.findUserEntity(username);

        if (wishRepository.countByUser(user.getId()) > 0) {
            throw new InvalidEndpointRequestException("This endpoint is not available", "The user already has a wish. This endpoint is no longer available.");
        }

        var incomeExpense = wishUtils.getTotalIncomeAndExpenseByUser(user);

        if (wish.getProposalAmount() > wishUtils.getProposalAmount(incomeExpense.get("totalIncome"), incomeExpense.get("totalExpense")) * 2)
            throw new AddError("First Wish", new Throwable());

        var wishEntity = mapper.map(wish, WishEntity.class);
        wishEntity.setUser(user);
        wishEntity.setDate(wishUtils.calculateData(wish.getTotal(), 0.00, wish.getProposalAmount()));

        try {
            return mapper.map(wishRepository.save(wishEntity), Wish.class);
        } catch (DataIntegrityViolationException c) {
            throw new AddError("Wish", c);
        }
    }
}
