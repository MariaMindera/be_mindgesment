package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.TokenEmailEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidToken;
import com.mindera.school.mindgesment.http.models.EmailChange;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenEmailGenerator;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ChangeEmailServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperFacade mapper;

    @Mock
    private TokenEmailGenerator tokenGenerator;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ChangeEmailServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void requestChangeEmail() {
        //given
        var username = "username";
        var email = "a@a.com";
        var userId = "userId";

        var user = new UserEntity(email, "password", username, CoinEntity.EUR);
        user.setId(userId);

        var token = "token";

        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId), eq(null)))
                .thenReturn(token);

        subject.requestChangeEmail(username);

        //then
        verify(userRepository, times(1))
                .findByUsername(eq(username));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId), eq(null));
        verify(emailSender, times(1))
                .sendChangeEmail(eq(username), eq(email), eq(token));
    }

    @Test
    void requestChangeEmailNotSendEmail() {
        //given
        var username = "username";
        var email = "a@a.com";
        var userId = "userId";

        var user = new UserEntity(email, "password", username, CoinEntity.EUR);
        user.setId(userId);

        //when
        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId), eq(null)))
                .thenReturn(null);

        subject.requestChangeEmail(username);

        //then
        verify(userRepository, times(1))
                .findByUsername(eq(username));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId), eq(null));
        verify(emailSender, times(0))
                .sendChangeEmail(eq(username), eq(email), any(String.class));
    }

    @Test
    void changeEmail() {
        //given
        var username = "username";
        var userId = "userId";
        var user = new UserEntity("a@a.com", "password", username, CoinEntity.EUR);
        user.setId(userId);

        var tokenId = "tokenId";
        var token = new TokenEmailEntity();
        token.setId(tokenId);
        token.setUserId(userId);

        var newToken = "newToken";

        var email = "a@a.com";
        var emailChange = new EmailChange();
        emailChange.setEmail(email);
        emailChange.setToken(tokenId);

        //when
        when(tokenGenerator.findToken(eq(tokenId)))
                .thenReturn(token);
        when(userRepository.findById(eq(userId)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId), eq(email)))
                .thenReturn(newToken);

        subject.changeEmail(emailChange);

        //then
        verify(tokenGenerator, times(1))
                .findToken(eq(tokenId));
        verify(userRepository, times(1))
                .findById(eq(userId));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId), eq(email));
        verify(emailSender, times(1))
                .sendConfirmNewEmail(eq(username), eq(email), eq(newToken));
        verify(tokenGenerator, times(1))
                .deleteToken(eq(tokenId));
    }

    @Test
    void changeEmailInvalidToken() {
        //given
        var tokenId = "tokenId";
        var emailChange = new EmailChange();
        emailChange.setToken(tokenId);

        //when
        when(tokenGenerator.findToken(eq(tokenId)))
                .thenReturn(null);

        //then
        assertThrows(InvalidToken.class, () -> subject.changeEmail(emailChange));
        verify(tokenGenerator, times(1))
                .findToken(eq(tokenId));
        verify(userRepository, times(0))
                .findById(any());
        verify(mapper, times(0))
                .map(any(), any());
        verify(tokenGenerator, times(0))
                .create(any(), anyString());
        verify(emailSender, times(0))
                .sendConfirmNewEmail(anyString(), anyString(), anyString());
        verify(tokenGenerator, times(0))
                .deleteToken(anyString());
    }

    @Test
    void changeEmailNewTokenNull() {
        //given
        var userId = "userId";
        var user = new UserEntity("a@a.com", "password", "username", CoinEntity.EUR);
        user.setId(userId);

        var tokenId = "tokenId";
        var token = new TokenEmailEntity();
        token.setId(tokenId);
        token.setUserId(userId);

        var email = "a@a.com";
        var emailChange = new EmailChange();
        emailChange.setEmail(email);
        emailChange.setToken(tokenId);

        //when
        when(tokenGenerator.findToken(eq(tokenId)))
                .thenReturn(token);
        when(userRepository.findById(eq(userId)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);
        when(tokenGenerator.create(eq(userId), eq(email)))
                .thenReturn(null);

        subject.changeEmail(emailChange);

        //then
        verify(tokenGenerator, times(1))
                .findToken(eq(tokenId));
        verify(userRepository, times(1))
                .findById(eq(userId));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(tokenGenerator, times(1))
                .create(eq(userId), eq(email));
        verify(emailSender, times(0))
                .sendConfirmNewEmail(anyString(), anyString(), anyString());
        verify(tokenGenerator, times(0))
                .deleteToken(anyString());
    }

    @Test
    void confirmChangeEmail() {
        //given
        var token = "token";

        var userId = "userId";
        var newEmail = "newEmail";
        var tokenEntity = new  TokenEmailEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setNewEmail(newEmail);

        var user = new UserEntity();

        var userNewEmail = new UserEntity();
        userNewEmail.setEmail(newEmail);

        //when
        when(tokenGenerator.findToken(eq(token)))
                .thenReturn(tokenEntity);
        when(userRepository.findById(eq(userId)))
                .thenReturn(Optional.of(user));
        when(mapper.map(eq(user), eq(UserEntity.class)))
                .thenReturn(user);

        subject.confirmChangeEmail(token);

        //then
        verify(tokenGenerator, times(1))
                .findToken(eq(token));
        verify(userRepository,times(1))
                .findById(eq(userId));
        verify(mapper, times(1))
                .map(eq(user), eq(UserEntity.class));
        verify(userRepository, times(1))
                .save(eq(userNewEmail));
        verify(tokenGenerator, times(1))
                .deleteToken(token);
    }

    @Test
    void confirmChangeEmailInvalidToken() {
        //given
        var token = "token";

        //when
        when(tokenGenerator.findToken(eq(token)))
                .thenReturn(null);

        //then
        assertThrows(InvalidToken.class, () -> subject.confirmChangeEmail(token));

        verify(tokenGenerator, times(1))
                .findToken(eq(token));
        verify(userRepository,times(0))
                .findById(anyString());
        verify(mapper, times(0))
                .map(anyString(), any());
        verify(userRepository, times(0))
                .save(any());
        verify(tokenGenerator, times(0))
                .deleteToken(anyString());
    }
}