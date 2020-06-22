package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.PasswordReset;

public interface ChangePasswordService {

    void requestChangePassword(String username);

    void changePassword(PasswordReset passwordReset);
}
