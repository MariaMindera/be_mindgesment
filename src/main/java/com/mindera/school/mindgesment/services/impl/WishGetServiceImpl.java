package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.InvalidPage;
import com.mindera.school.mindgesment.exceptions.NotExits;
import com.mindera.school.mindgesment.http.models.PaginatedTransactions;
import com.mindera.school.mindgesment.http.models.PaginatedWishes;
import com.mindera.school.mindgesment.http.models.Transaction;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishGetService;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WishGetServiceImpl implements WishGetService {

    private final WishRepository wishRepository;

    private final TransactionRepository transactionRepository;

    private final WishUtilsService wishUtils;

    private final MapperFacade mapper;

    @Autowired
    public WishGetServiceImpl(WishRepository wishRepository, TransactionRepository transactionRepository, WishUtilsService wishUtils, MapperFacade mapper) {
        this.wishRepository = wishRepository;
        this.transactionRepository = transactionRepository;
        this.wishUtils = wishUtils;
        this.mapper = mapper;
    }

    @Override
    public PaginatedWishes findAll(String username, Integer page) {
        var user = wishUtils.findUserEntity(username);
        var list = wishRepository.findAllByCompletedAndUser(false, user, PageRequest.of(page, 10, Sort.by("createdIn").descending()))
                .stream()
                .map(wishEntity -> {
                    var wish = mapper.map(wishEntity, Wish.class);
                    wish.setBalance(wishUtils.getBalanceWish(wishEntity));
                    return wish;
                })
                .collect(Collectors.toList());

        var completedList = wishRepository.findAllByUser(user.getId()).stream()
                .map(wishEntity -> {
                    var wish = mapper.map(wishEntity, Wish.class);
                    wish.setBalance(wishUtils.getBalanceWish(wishEntity));
                    return wish;
                })
                .filter(wish -> wish.getTotal().equals(wish.getBalance()))
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedWishes = new PaginatedWishes();
        paginatedWishes.setList(list);
        paginatedWishes.setCompleted(completedList.isEmpty() ? null : completedList);
        paginatedWishes.setTotal(wishRepository.countByUser(user.getId()));
        return paginatedWishes;
    }

    @Override
    public PaginatedTransactions findAllTransactions(String username, String wishId, Integer page) {
        var user = wishUtils.findUserEntity(username);

        var wish = wishRepository.findById(wishId)
                .map(wishEntity -> mapper.map(wishEntity, WishEntity.class))
                .orElseThrow(() -> new NotExits("wish", wishId));

        if (! wish.getUser().equals(user))
            throw new NotExits("Wish", wishId);

        var list = transactionRepository.findAllByWish(wish, PageRequest.of(page, 10, Sort.by("date", "createdIn").descending()));

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedTransactions = new PaginatedTransactions();
        paginatedTransactions.setList(
                list.stream()
                        .map(transactionEntity -> mapper.map(transactionEntity, Transaction.class))
                        .collect(Collectors.toList())
        );
        paginatedTransactions.setTotal((long) wish.getTransactions().size());
        return paginatedTransactions;
    }
}
