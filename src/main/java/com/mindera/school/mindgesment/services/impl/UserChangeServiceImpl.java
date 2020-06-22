package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.EditError;
import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.Coin;
import com.mindera.school.mindgesment.http.models.User;
import com.mindera.school.mindgesment.services.UserChangeService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserChangeServiceImpl implements UserChangeService {

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    @Autowired
    public UserChangeServiceImpl(UserRepository userRepository, MapperFacade mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User changeUsername(String username, String usernameChange) {
        var userChange = findUserEntity(username);
        userChange.setUsername(usernameChange);

        try {
            return mapper.map(userRepository.save(userChange), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("username of user", null);
        }
    }

    @Override
    public User changeCoin(String username, String coin) {
        var userChange = findUserEntity(username);

        if (Arrays.stream(Coin.values())
                .noneMatch(coins -> coins.toString()
                        .equals(coin)))
            throw new InvalidParameter("coin", Arrays.toString(Coin.values()));

        userChange.setCoin(mapper.map(coin, CoinEntity.class));

        try {
            return mapper.map(userRepository.save(userChange), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("coin of user", e);
        }
    }

    @Override
    public User changeEarnings(String username, Double earnings) {
        var userChange = findUserEntity(username);
        userChange.setEarnings(earnings);

        try {
            return mapper.map(userRepository.save(userChange), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("earnings of user", e);
        }
    }

    @Override
    public User changeExpenses(String username, Double expenses) {
        var userChange = findUserEntity(username);
        userChange.setExpenses(expenses);

        try {
            return mapper.map(userRepository.save(userChange), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("expenses of user", e);
        }
    }

    @Override
    public User changeMonthlyPeriod(String username, Integer monthlyPeriod) {
        var user = findUserEntity(username);
        user.setMonthlyPeriod(monthlyPeriod);
        try {
            return mapper.map(userRepository.save(user), User.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("monthly period of user", e);
        }
    }

    private UserEntity findUserEntity(String username) {
        return userRepository.findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));
    }
}
