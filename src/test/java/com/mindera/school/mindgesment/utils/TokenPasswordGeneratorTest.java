package com.mindera.school.mindgesment.utils;

import com.mindera.school.mindgesment.data.entities.TokenPasswordEntity;
import com.mindera.school.mindgesment.data.repositories.TokenPasswordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TokenPasswordGeneratorTest {

    @Mock
    private TokenPasswordRepository tokenPasswordRepository;

    @InjectMocks
    private TokenPasswordGenerator subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create() {
        //given
        var userId = "userId";

        var tokenId = "tokenId";
        var token = new TokenPasswordEntity();
        token.setId(tokenId);

        //when
        when(tokenPasswordRepository.existsByUserId(eq(userId)))
                .thenReturn(false);
        when(tokenPasswordRepository.save(any(TokenPasswordEntity.class)))
                .thenReturn(token);

        var response = subject.create(userId);

        //then
        verify(tokenPasswordRepository, times(1))
                .existsByUserId(eq(userId));
        verify(tokenPasswordRepository, times(0))
                .deleteByUserId(anyString());
        verify(tokenPasswordRepository, times(1))
                .save(any(TokenPasswordEntity.class));
        assertEquals(tokenId, response);
    }

    @Test
    void createWithDuplicateToken() {
        //given
        var userId = "userId";

        var tokenId = "tokenId";
        var token = new TokenPasswordEntity();
        token.setId(tokenId);

        //when
        when(tokenPasswordRepository.existsByUserId(eq(userId)))
                .thenReturn(true);
        when(tokenPasswordRepository.save(any(TokenPasswordEntity.class)))
                .thenReturn(token);

        var response = subject.create(userId);

        //then
        verify(tokenPasswordRepository, times(1))
                .existsByUserId(eq(userId));
        verify(tokenPasswordRepository, times(1))
                .deleteByUserId(eq(userId));
        verify(tokenPasswordRepository, times(1))
                .save(any(TokenPasswordEntity.class));
        assertEquals(tokenId, response);
    }

    @Test
    void findToken() {
        //given
        var userId = "userId";
        var tokenId = "tokenId";

        var token = new TokenPasswordEntity();

        token.setUserId(userId);
        token.setId(tokenId);

        //when
        when(tokenPasswordRepository.findById(eq(tokenId)))
                .thenReturn(Optional.of(token));

        var response = subject.findToken(tokenId);

        //then
        verify(tokenPasswordRepository, times(1))
                .findById(eq(tokenId));
        assertEquals(userId, response);
    }

    @Test
    void deleteToken() {
        //given
        var token = "tokenId";

        //when
        subject.deleteToken(token);

        //then
        verify(tokenPasswordRepository, times(1))
                .deleteById(eq(token));
    }

    @Test
    void expire() {
        //when
        subject.expire();

        //then
        verify(tokenPasswordRepository, times(1))
                .deleteByCreatedAtIsBefore(any(LocalDateTime.class));
    }
}