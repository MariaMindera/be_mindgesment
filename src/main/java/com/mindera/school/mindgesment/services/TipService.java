package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.Tip;

public interface TipService {

    void create(String username, Tip tip);

    Tip edit(String username, String tipId, Tip editTip);

    void delete(String username, String tipId);

    void validateUserAdmin(String username);

    void delete(String tipId);
}
