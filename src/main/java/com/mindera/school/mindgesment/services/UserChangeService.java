package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.User;

public interface UserChangeService {

    User changeUsername(String username, String usernameChange);

    User changeCoin(String username, String coin);

    User changeEarnings(String username, Double earnings);

    User changeExpenses(String username, Double expenses);

    User changeMonthlyPeriod(String username, Integer monthlyPeriod);
}
