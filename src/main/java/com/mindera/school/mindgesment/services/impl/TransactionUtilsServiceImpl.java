package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.*;
import com.mindera.school.mindgesment.services.TransactionUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TransactionUtilsServiceImpl implements TransactionUtilsService {

    private final MapperFacade mapper;

    private final UserRepository userRepository;

    @Autowired
    public TransactionUtilsServiceImpl(MapperFacade mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public void verifyCategoryWithType(final Transaction transaction) {
        switch (transaction.getType()) {
            case "INCOME":
                if (Arrays.stream(CategoryIncome.values())
                        .noneMatch(categoryIncome -> categoryIncome.toString()
                                .equals(transaction.getCategory())))
                    throw new InvalidParameter("category", Arrays.toString(CategoryIncome.values()));
                break;
            case "EXPENSE":
                if (Arrays.stream(CategoryExpense.values())
                        .noneMatch(categoryExpense -> categoryExpense.toString()
                                .equals(transaction.getCategory())))
                    throw new InvalidParameter("category", Arrays.toString(CategoryExpense.values()));
                break;
            default:
                throw new InvalidParameter("transaction type", Arrays.toString(TransactionType.values()));
        }
    }

    @Override
    public UserEntity findUserEntity(final String username) {
        return userRepository
                .findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));
    }

    @Override
    public void verifyAllCategory(String category) {
        if (Arrays.stream(Category.values())
                .noneMatch(category1 -> category1.toString()
                        .equals(category)))
            throw new InvalidParameter("category", Arrays.toString(Category.values()));
    }

    @Override
    public void verifyAllTransactionTypes(String transactionType) {
        if (Arrays.stream(TransactionType.values())
                .noneMatch(transactionType1 -> transactionType1.toString()
                        .equals(transactionType)))
            throw new InvalidParameter("transaction type", Arrays.toString(TransactionType.values()));
    }
}
