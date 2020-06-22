package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.*;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionUtilsServiceImplTest {

    @Mock
    private MapperFacade mapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionUtilsServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void verifyCategoryWithType() {
        //given
        var transactionValidIncome = new Transaction();
        transactionValidIncome.setType(TransactionType.INCOME.toString());
        transactionValidIncome.setCategory(CategoryIncome.SALARY.toString());

        var transactionValidExpense = new Transaction();
        transactionValidExpense.setType(TransactionType.EXPENSE.toString());
        transactionValidExpense.setCategory(CategoryExpense.FOOD_DRINK.toString());

        var transactionInvalidIncome = new Transaction();
        transactionInvalidIncome.setType(TransactionType.INCOME.toString());
        transactionInvalidIncome.setCategory(CategoryExpense.FOOD_DRINK.toString());

        var transactionInvalidExpense = new Transaction();
        transactionInvalidExpense.setType(TransactionType.EXPENSE.toString());
        transactionInvalidExpense.setCategory(CategoryIncome.SALARY.toString());

        var transactionInvalidType = new Transaction();
        transactionInvalidType.setType("INVALID_TYPE");

        //when
        subject.verifyCategoryWithType(transactionValidIncome);
        subject.verifyCategoryWithType(transactionValidExpense);

        //then
        assertThrows(InvalidParameter.class, () -> subject.verifyCategoryWithType(transactionInvalidIncome));
        assertThrows(InvalidParameter.class, () -> subject.verifyCategoryWithType(transactionInvalidExpense));
        assertThrows(InvalidParameter.class, () -> subject.verifyCategoryWithType(transactionInvalidType));
    }

    @Test
    void findUserEntity() {
        //given
        var username = "username";
        var user = new UserEntity("email", "***", "username", CoinEntity.EUR);

        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);

        var response = subject.findUserEntity(username);

        //then
        assertEquals(user, response);
        verify(userRepository, times(1))
                .findByUsername(eq(username));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
    }

    @Test
    void findUserEntityError() {
        //given
        var username = "username";
        var user = new UserEntity("email", "***", "username", CoinEntity.EUR);

        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.empty());

        //then
        var response = assertThrows(UserNotExists.class, () -> subject.findUserEntity(username));
        assertEquals(String.format("User with username=%s not found.", username), response.getMessage());
        verify(userRepository, times(1))
                .findByUsername(eq(username));
    }

    @Test
    void verifyAllCategory() {
        //given
        var categoryValid = Category.FOOD_DRINK.toString();
        var categoryInvalid = "INVALID_CATEGORY";

        //when
        subject.verifyAllCategory(categoryValid);

        //then
        assertThrows(InvalidParameter.class, () -> subject.verifyAllCategory(categoryInvalid));
    }

    @Test
    void verifyAllTransactionTypes() {
        //given
        var typeValid = TransactionType.EXPENSE.toString();
        var typeInvalid = "INVALID_TYPE";

        //when
        subject.verifyAllTransactionTypes(typeValid);

        //then
        assertThrows(InvalidParameter.class, () -> subject.verifyAllTransactionTypes(typeInvalid));
    }
}