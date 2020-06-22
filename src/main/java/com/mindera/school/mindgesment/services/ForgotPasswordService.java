package com.mindera.school.mindgesment.services;


import com.mindera.school.mindgesment.http.models.PasswordForgot;

public interface ForgotPasswordService {

    void processForgotPassword(PasswordForgot passwordForgot);
}
