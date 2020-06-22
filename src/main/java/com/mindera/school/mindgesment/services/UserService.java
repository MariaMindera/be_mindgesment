package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.User;

import java.util.Map;

public interface UserService {

    User getUser(String username);

    Map<String, Object> getUserBalance(String username);

    User changeEmail(String username, String email);

    User changeUsername(String username, String usernameChange);

    User changeCoin(String username, String coin);

    User changeEarnings(String username, Double earnings);

    User changeExpenses(String username, Double expenses);

    User changeMonthlyPeriod(String username, Long monthlyPeriod);
}
