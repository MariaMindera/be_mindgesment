package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.User;

import java.util.Map;

public interface UserGetInformationService {

    User getUser(String username);

    Map<String, Double> getUserBalance(String username);

    Map<String, Double> getUserAllBalance(String username);
}
