package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.AddError;
import com.mindera.school.mindgesment.http.models.Wish;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;

import java.time.YearMonth;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class WishFirstServiceImplTest {

    @Mock
    private WishUtilsService wishUtils;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private WishFirstServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void getProposalAmount() {
        //given
        var username = "username";
        var totalAmount = 1000L;
        var user = new UserEntity("email", "***", username, CoinEntity.EUR);

        var totalIncome = 500.00;
        var totalExpense = 200.00;

        var incomeExpense = new HashMap<String, Double>();
        incomeExpense.put("totalIncome", totalIncome);
        incomeExpense.put("totalExpense", totalExpense);

        var proposalAmount = 100.00;
        var date = YearMonth.now();

        //when
        when(wishUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(wishUtils.getTotalIncomeAndExpenseByUser(eq(user)))
                .thenReturn(incomeExpense);
        when(wishUtils.getProposalAmount(eq(totalIncome), eq(totalExpense)))
                .thenReturn(proposalAmount);
        when(wishUtils.calculateData(eq((double) totalAmount), eq(0.00), eq(proposalAmount)))
                .thenReturn(date);

        var response = subject.getProposalAmount(username, totalAmount);

        //then
        assertEquals((long) proposalAmount, response.get("proposalAmount"));
        assertEquals(date, response.get("date"));

        verify(wishUtils, times(1))
                .findUserEntity(eq(username));
        verify(wishUtils, times(1))
                .getTotalIncomeAndExpenseByUser(eq(user));
        verify(wishUtils, times(1))
                .getProposalAmount(eq(totalIncome), eq(totalExpense));
        verify(wishUtils, times(1))
                .calculateData(eq((double) totalAmount), eq(0.00), eq(proposalAmount));
    }

    @Test
    void addNewWishProposalGreaterThanTotal() {
        //given
        var username = "username";
        var wish = new Wish("wish", 100L, 1000L);

        //then
        var response = assertThrows(AddError.class, () -> subject.addNewWish(username, wish));
        assertEquals("Invalid add Wish.", response.getMessage());
        assertEquals("The proposal amount must be less than total.", response.getCause().getMessage());
    }

    @Test
    void addNewWishCountByUserGreaterThan0() {
        //given
        var username = "username";
        var userId = "userId";

        var wish = new Wish("wish", 1000L, 100L);

        var user = new UserEntity("email", "***", username, CoinEntity.EUR);
        user.setId(userId);

        //when
        when(wishUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(wishRepository.countByUser(eq(userId)))
                .thenReturn(1L);

        //then
        var response = assertThrows(InvalidEndpointRequestException.class, () -> subject.addNewWish(username, wish));
        assertEquals("This endpoint is not available", response.getMessage());
        assertEquals("The user already has a wish. This endpoint is no longer available.", response.getReason());

        verify(wishUtils, times(1))
                .findUserEntity(eq(username));
        verify(wishRepository, times(1))
                .countByUser(eq(userId));
    }
}