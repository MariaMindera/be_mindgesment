package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.AddError;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishOthersService;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WishOthersServiceImpl implements WishOthersService {

    private final WishUtilsService wishUtils;

    private final WishRepository wishRepository;

    private final MapperFacade mapper;

    @Autowired
    public WishOthersServiceImpl(WishUtilsService wishUtils, WishRepository wishRepository, MapperFacade mapper) {
        this.wishUtils = wishUtils;
        this.wishRepository = wishRepository;
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> getProposalAmount(String username) {
        var user = wishUtils.findUserEntity(username);

        if (wishRepository.countByUser(user.getId()) == 0) {
            throw new InvalidEndpointRequestException("This endpoint is not available", "The user has no wish. This endpoint is no available.");
        }

        var response = new HashMap<String, Object>();
        response.put(
                "list",
                wishRepository.findAllByUser(user.getId()).stream()
                        .map(wishEntity -> mapper.map(wishEntity, Wish.class))
                        .collect(Collectors.toList())
        );
        var incomeExpense = wishUtils.getTotalIncomeAndExpenseByUser(user);
        response.put("proposalAmount", wishUtils.getProposalAmount(incomeExpense.get("totalIncome"), incomeExpense.get("totalExpense")));
        return response;
    }

    @Override
    public void addWish(String username, Wish newWish, List<Wish> othersWishes) {
        var user = wishUtils.findUserEntity(username);

        if (wishRepository.countByUser(user.getId()) == 0) {
            throw new InvalidEndpointRequestException("This endpoint is not available", "The user has no wish. This endpoint is no available.");
        }

        if (othersWishes == null || othersWishes.isEmpty()) {
            throw new AddError("Others Wishes", new Throwable("The list is empty."));
        }

        var list = othersWishes.stream()
                .map(wish -> mapper.map(wish, WishEntity.class))
                .peek(wishEntity -> {
                    wishEntity.setUser(user);
                    wishEntity.setDate(
                            wishUtils.calculateData(
                                    wishEntity.getTotal(),
                                    (double) wishUtils.getBalanceWish(wishEntity),
                                    wishEntity.getProposalAmount())
                    );
                }).collect(Collectors.toList());

        var incomeExpense = wishUtils.getTotalIncomeAndExpenseByUser(user);

        var sumAllProposalAmount = list.stream()
                .map(WishEntity::getProposalAmount)
                .reduce(0L, Long::sum)
                + newWish.getProposalAmount();

        var proposalAmount = wishUtils.getProposalAmount(
                incomeExpense.get("totalIncome"),
                incomeExpense.get("totalExpense"));

        if (sumAllProposalAmount > proposalAmount * 2)
            throw new AddError("Others Wishes", new Throwable());

        var wish = mapper.map(newWish, WishEntity.class);
        wish.setDate(wishUtils.calculateData(newWish.getTotal(), 0.00, newWish.getProposalAmount()));
        wish.setUser(user);

        try {
            list.forEach(wishRepository::save);
            wishRepository.save(wish);
        } catch (DataIntegrityViolationException e) {
            throw new AddError("Others Wishes", e);
        }
    }
}
