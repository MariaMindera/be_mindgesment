package com.mindera.school.mindgesment.utils;

import com.mindera.school.mindgesment.data.entities.TokenEmailEntity;
import com.mindera.school.mindgesment.data.repositories.TokenEmailRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TokenEmailGeneratorTest {

    @Mock
    private TokenEmailRepository tokenEmailRepository;

    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private TokenEmailGenerator subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create() {
        //given
        var userId = "userId";
        var token = new TokenEmailEntity();
        var tokenId = "tokenId";
        token.setId(tokenId);

        //when
        when(tokenEmailRepository.existsByUserId(eq(userId)))
                .thenReturn(false);
        when(tokenEmailRepository.save(isA(TokenEmailEntity.class)))
                .thenReturn(token);

        var response = subject.create(userId, "newEmail");

        //then
        verify(tokenEmailRepository, times(1))
                .existsByUserId(eq(userId));
        verify(tokenEmailRepository, times(0))
                .deleteByUserId(anyString());
        verify(tokenEmailRepository, times(1))
                .save(isA(TokenEmailEntity.class));
        assertEquals(tokenId, response);
    }

    @Test
    void createWithDuplicateToken() {
        //given
        var userId = "userId";
        var token = new TokenEmailEntity();
        var tokenId = "tokenId";
        token.setId(tokenId);

        //when
        when(tokenEmailRepository.existsByUserId(eq(userId)))
                .thenReturn(true);
        when(tokenEmailRepository.save(isA(TokenEmailEntity.class)))
                .thenReturn(token);

        var response = subject.create(userId, "newEmail");

        //then
        verify(tokenEmailRepository, times(1))
                .existsByUserId(eq(userId));
        verify(tokenEmailRepository, times(1))
                .deleteByUserId(eq(userId));
        verify(tokenEmailRepository, times(1))
                .save(isA(TokenEmailEntity.class));
        assertEquals(tokenId, response);
    }

    @Test
    void findToken() {
        //given
        var userId = "userId";
        var tokenId = "tokenId";

        var token = new TokenEmailEntity();

        token.setUserId(userId);
        token.setId(tokenId);

        //when
        when(tokenEmailRepository.findById(eq(tokenId)))
                .thenReturn(Optional.of(token));
        when(mapper.map(eq(token), eq(TokenEmailEntity.class)))
                .thenReturn(token);

        var response = subject.findToken(tokenId);

        //then
        verify(tokenEmailRepository, times(1))
                .findById(eq(tokenId));
        assertEquals(token, response);
    }

    @Test
    void deleteToken() {
        //given
        var token = "tokenId";

        //when
        subject.deleteToken(token);

        //then
        verify(tokenEmailRepository, times(1))
                .deleteById(eq(token));
    }

    @Test
    void expire() {
        //when
        subject.expire();

        //then
        verify(tokenEmailRepository, times(1))
                .deleteByCreatedAtIsBefore(isA(LocalDateTime.class));
    }
}