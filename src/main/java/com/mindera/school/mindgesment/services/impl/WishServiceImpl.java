package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CategoryEntity;
import com.mindera.school.mindgesment.data.entities.TransactionEntity;
import com.mindera.school.mindgesment.data.entities.TransactionTypeEntity;
import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.*;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishService;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;

    private final TransactionRepository transactionRepository;

    private final WishUtilsService wishUtils;

    private final MapperFacade mapper;

    @Autowired
    public WishServiceImpl(WishRepository wishRepository, TransactionRepository transactionRepository, WishUtilsService wishUtils, MapperFacade mapper) {
        this.wishRepository = wishRepository;
        this.transactionRepository = transactionRepository;
        this.wishUtils = wishUtils;
        this.mapper = mapper;
    }

    @Override
    public Long getBalance(final String username) {
        return wishRepository.findAllByUser(wishUtils.findUserEntity(username).getId()).stream()
                .map(wishUtils::getBalanceWish)
                .reduce(0L, Long::sum);
    }

    @Override
    public Double getProposalAmount(String username) {
        var user = wishUtils.findUserEntity(username);

        var incomeExpense = wishUtils.getTotalIncomeAndExpenseByUser(user);
        var proposalAmount = wishUtils.getProposalAmount(incomeExpense.get("totalIncome"), incomeExpense.get("totalExpense"));

        var totalProposalAmountWishes = user.getWishes().stream()
                .map(WishEntity::getProposalAmount)
                .reduce(0L, Long::sum);

        return proposalAmount * 2 - totalProposalAmountWishes;
    }

    @Override
    public void addBalanceWish(String username, String wishId, Long balance) {
        var wishDatabase = wishUtils.findWishEntity(wishId);
        var user = wishUtils.findUserEntity(username);

        if (! wishDatabase.getUser().equals(user))
            throw new NotExits("Wish", wishId);

        var wishDatabaseBalance = wishUtils.getBalanceWish(wishDatabase);

        if (wishDatabaseBalance + balance > wishDatabase.getTotal())
            throw new BalanceExceededTotalWish(
                    wishDatabase.getTotal(),
                    wishDatabase.getTotal() - wishDatabaseBalance
            );

        var transaction = new TransactionEntity();
        transaction.setTitle(String.format("Wish: %s", wishDatabase.getName()));
        transaction.setAmount(Double.valueOf(balance));
        transaction.setDate(LocalDate.now());
        transaction.setType(TransactionTypeEntity.EXPENSE);
        transaction.setCategory(CategoryEntity.SAVINGS);
        transaction.setWish(wishDatabase);
        transaction.setUser(user);

        try {
            transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException c) {
            throw new AddError("balance to wish", c);
        }
    }

    @Override
    public Wish editWish(final String username, final String wishId, final Wish editedWish) {
        if (editedWish.getProposalAmount() > editedWish.getTotal()) {
            throw new EditError("Wish", new Throwable("The proposal amount must be less than total."));
        }

        var wish = mapper.map(editedWish, WishEntity.class);
        wish.setId(wishId);
        wish.setUser(wishUtils.findUserEntity(username));
        wish.setCompleted(false);

        var wishDatabase = wishUtils.findWishEntity(wishId);
        wish.setDate(wishUtils.calculateData(wish.getTotal(), (double) wishUtils.getBalanceWish(wishDatabase), wish.getProposalAmount()));
        wish.setCreatedIn(wishDatabase.getCreatedIn());

        if (! wishDatabase.getUser()
                .equals(wish.getUser()))
            throw new NotExits("Wish", wishId);

        try {
            return mapper.map(wishRepository.save(wish), Wish.class);
        } catch (DataIntegrityViolationException c) {
            throw new EditError("Wish", c);
        }
    }

    @Override
    public void deleteWish(final String username, final String wishId) {
        var wishDatabase = wishUtils.findWishEntity(wishId);

        if (! wishDatabase.getUser()
                .equals(wishUtils.findUserEntity(username)))
            throw new NotExits("Wish", wishId);

        try {
            wishDatabase.getTransactions()
                    .forEach(transactionEntity -> transactionRepository.deleteById(transactionEntity.getId()));
            delete(wishId);
        } catch (DataIntegrityViolationException c) {
            throw new DeleteError("Wish", c);
        }
    }

    @Override
    public void completeWish(String username, String wishId) {
        var wishDatabase = wishUtils.findWishEntity(wishId);

        if (! wishDatabase.getUser()
                .equals(wishUtils.findUserEntity(username)))
            throw new NotExits("Wish", wishId);

        if (! wishDatabase.getTotal()
                .equals(wishUtils.getBalanceWish(wishDatabase)))
            throw new EditError("Wish (Complete)", new Throwable("The total isn't equal to the balance total. It isn't yet complete."));

        wishDatabase.setCompleted(true);

        try {
            wishRepository.save(wishDatabase);
        } catch (DataIntegrityViolationException c) {
            throw new EditError("Wish (Complete)", c);
        }
    }

    @Transactional
    @Override
    public void delete(String wishId) {
        wishRepository.deleteById(wishId);
    }
}
