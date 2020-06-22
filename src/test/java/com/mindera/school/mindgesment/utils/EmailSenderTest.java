package com.mindera.school.mindgesment.utils;

import com.sendgrid.SendGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class EmailSenderTest {

    private String forgotPasswordTemplateId;

    private String welcomeTemplateId;

    @Mock
    private SendGrid sg;

    @InjectMocks
    private EmailSender subject;

    @BeforeEach
    void setUp() {

        this.forgotPasswordTemplateId = "forgot password key";
        this.welcomeTemplateId = "welcome key";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendWelcomeEmail() {
        String username = "username";
        String email = "email";
    }

    @Test
    void sendEmailForgotPassword() {
    }

    @Test
    void sendEmail() {
    }
}