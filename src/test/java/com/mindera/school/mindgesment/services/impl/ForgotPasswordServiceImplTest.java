package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.http.models.PasswordForgot;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenPasswordGenerator;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ForgotPasswordServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperFacade mapper;

    @Mock
    private TokenPasswordGenerator tokenGenerator;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ForgotPasswordServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void processForgotPassword() {
        //given
        var email = "email";
        var passwordForgot = new PasswordForgot();
        passwordForgot.setEmail(email);

        var username = "username";
        var userId = "userId";
        var user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setId(userId);

        var token = "token";

        //when
        when(userRepository.findByEmail(eq(email)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId)))
                .thenReturn(token);

        subject.processForgotPassword(passwordForgot);

        //then
        verify(userRepository, times(1))
                .findByEmail(eq(email));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId));
        verify(emailSender, times(1))
                .sendEmailForgotPassword(eq(username), eq(email), eq(token));
    }

    @Test
    void processForgotPasswordTokenNull() {
        //given
        var email = "email";
        var passwordForgot = new PasswordForgot();
        passwordForgot.setEmail(email);

        var username = "username";
        var userId = "userId";
        var user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setId(userId);

        //when
        when(userRepository.findByEmail(eq(email)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId)))
                .thenReturn(null);

        subject.processForgotPassword(passwordForgot);

        //then
        verify(userRepository, times(1))
                .findByEmail(eq(email));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId));
        verify(emailSender, times(0))
                .sendEmailForgotPassword(anyString(), anyString(), anyString());
    }
}