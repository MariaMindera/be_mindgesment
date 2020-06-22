package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.EmailChange;

public interface ChangeEmailService {

    void requestChangeEmail(String username);

    void changeEmail(EmailChange emailChange);

    void confirmChangeEmail(String token);
}
