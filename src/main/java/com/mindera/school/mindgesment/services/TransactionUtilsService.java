package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.http.models.Transaction;

public interface TransactionUtilsService {

    void verifyCategoryWithType(Transaction transaction);

    UserEntity findUserEntity(String username);

    void verifyAllCategory(String category);

    void verifyAllTransactionTypes(String transactionType);
}
