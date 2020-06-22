package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidToken;
import com.mindera.school.mindgesment.http.models.PasswordReset;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenPasswordGenerator;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ChangePasswordServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperFacade mapper;

    @Mock
    private TokenPasswordGenerator tokenGenerator;

    @Mock
    private EmailSender emailSender;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ChangePasswordServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void requestChangePassword() {
        //given
        var userId = "userId";
        var username = "username";
        var email = "email";

        var user = new UserEntity(email, "password", username, CoinEntity.EUR);
        user.setId(userId);

        var token = "token";


        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId)))
                .thenReturn(token);

        subject.requestChangePassword(username);

        //then
        verify(userRepository, times(1))
                .findByUsername(eq(username));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId));
        verify(emailSender, times(1))
                .sendChangePassword(eq(username), eq(email), eq(token));

    }

    @Test
    void requestChangePasswordTokenNull() {
        //given
        var userId = "userId";
        var username = "username";
        var email = "email";

        var user = new UserEntity(email, "password", username, CoinEntity.EUR);
        user.setId(userId);


        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId)))
                .thenReturn(null);

        subject.requestChangePassword(username);

        //then
        verify(userRepository, times(1))
                .findByUsername(eq(username));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId));
        verify(emailSender, times(0))
                .sendChangePassword(anyString(), anyString(), anyString());

    }

    @Test
    void changePassword() {
        //given
        var password = "password";
        var token = "token";

        var passwordReset = new PasswordReset(password, token);

        var userId = "userId";

        var user = new UserEntity("email", password, "username", CoinEntity.EUR);

        var passwordEncrypted = "passwordEncrypted";

        //when
        when(tokenGenerator.findToken(eq(token)))
                .thenReturn(userId);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(bCryptPasswordEncoder.encode(eq(password)))
                .thenReturn(passwordEncrypted);

        subject.changePassword(passwordReset);

        //then
        verify(tokenGenerator, times(1))
                .findToken(eq(token));
        verify(userRepository, times(1))
                .findById(eq(userId));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(bCryptPasswordEncoder, times(1))
                .encode(eq(password));
        verify(userRepository, times(1))
                .save(eq(new UserEntity("email", passwordEncrypted, "username", CoinEntity.EUR)));
        verify(tokenGenerator, times(1))
                .deleteToken(eq(token));
    }

    @Test
    void changePasswordUserIdNull() {
        //given
        var token = "token";
        var passwordReset = new PasswordReset("password", token);

        //when
        when(tokenGenerator.findToken(eq(token)))
                .thenReturn(null);

        //then
        assertThrows(InvalidToken.class, () -> subject.changePassword(passwordReset));

        verify(tokenGenerator, times(1))
                .findToken(eq(token));
        verify(userRepository, times(0))
                .findById(anyString());
        verify(mapper, times(0))
                .map(any(), any());
        verify(bCryptPasswordEncoder, times(0))
                .encode(anyString());
        verify(userRepository, times(0))
                .save(any());
        verify(tokenGenerator, times(0))
                .deleteToken(anyString());
    }
}