package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.RoleEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.User;
import com.mindera.school.mindgesment.services.UserGetInformationService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserGetInformationServiceImpl implements UserGetInformationService {

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    private final MapperFacade mapper;

    @Autowired
    public UserGetInformationServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository, MapperFacade mapper) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public User getUser(String username) {
        var userDb = userRepository.findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));

        var user = mapper.map(userDb, User.class);
        user.setRole(
                userDb.getRoles()
                        .stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList()
                        ));

        return user;
    }

    @Override
    public Map<String, Double> getUserBalance(String username) {
        var user = findUserEntity(username);

        LocalDate initialDate;

        var actualDay = MonthDay.now().getDayOfMonth();
        var actualMonth = MonthDay.now().getMonth();
        var actualYear = Year.now().getValue();
        var monthlyPeriod = user.getMonthlyPeriod();

        if (monthlyPeriod > actualDay) {
            if ((monthlyPeriod.equals(29) && actualMonth.equals(Month.MARCH) && ! Year.now().isLeap()) ||
                    (monthlyPeriod.equals(30) && actualMonth.equals(Month.MARCH)) ||
                    (monthlyPeriod.equals(31) && (actualMonth.equals(Month.MARCH) || actualMonth.equals(Month.MAY) || actualMonth.equals(Month.JULY) || actualMonth.equals(Month.OCTOBER) || actualMonth.equals(Month.DECEMBER)))
            ) {
                initialDate = LocalDate.of(actualYear, actualMonth, 1);
            } else {
                initialDate = LocalDate.of(actualYear, actualMonth.minus(1), monthlyPeriod);
            }
        } else {
            initialDate = LocalDate.of(actualYear, actualMonth, monthlyPeriod);
        }

        var response = new HashMap<String, Double>();
        response.put("income", Objects.requireNonNullElse(transactionRepository.sumIncomeInThisMonthByUser(user, initialDate, LocalDate.now()), 0.00));
        response.put("expense", Objects.requireNonNullElse(transactionRepository.sumExpenseInThisMonthByUser(user, initialDate, LocalDate.now()), 0.00));
        return response;
    }

    @Override
    public Map<String, Double> getUserAllBalance(String username) {
        var user = findUserEntity(username);

        var response = new HashMap<String, Double>();
        response.put("income", Objects.requireNonNullElse(transactionRepository.sumIncomeByUser(user), 0.00));
        response.put("expense", Objects.requireNonNullElse(transactionRepository.sumExpenseByUser(user), 0.00));
        return response;
    }

    private UserEntity findUserEntity(String username) {
        return userRepository.findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));
    }
}
