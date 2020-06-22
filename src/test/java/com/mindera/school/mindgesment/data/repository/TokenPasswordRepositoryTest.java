package com.mindera.school.mindgesment.data.repository;

import com.mindera.school.mindgesment.data.entities.TokenPasswordEntity;
import com.mindera.school.mindgesment.data.repositories.TokenPasswordRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class TokenPasswordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenPasswordRepository repository;

    @Test
    void deleteByCreatedAtIsBefore() {
        //given
        entityManager.persistAndFlush(new TokenPasswordEntity(LocalDateTime.of(2010, 1, 1, 11, 11)));
        var token2 = entityManager.persistAndFlush(new TokenPasswordEntity(LocalDateTime.now()));

        //when
        repository.deleteByCreatedAtIsBefore(LocalDateTime.now().minusHours(24));
        var found = repository.findAll();

        //then
        assertThat(found).containsExactly(token2);
    }

    @Test
    void deleteById() {
        //given
        var token1 = entityManager.persistAndFlush(new TokenPasswordEntity(LocalDateTime.now()));
        var token2 = entityManager.persistAndFlush(new TokenPasswordEntity(LocalDateTime.now()));

        //when
        repository.deleteById(token1.getId());
        var found = repository.findAll();

        //then
        assertThat(found).containsExactly(token2);
    }

    @Test
    void deleteByUserId() {
        //given
        var user1 = "user1";

        var token1user1 = new TokenPasswordEntity(LocalDateTime.now());
        token1user1.setUserId(user1);
        entityManager.persistAndFlush(token1user1);

        var token2user1 = new TokenPasswordEntity(LocalDateTime.now());
        token2user1.setUserId(user1);
        entityManager.persistAndFlush(token2user1);

        var user2 = "user2";
        var token1user2 = new TokenPasswordEntity(LocalDateTime.now());
        token1user2.setUserId(user2);
        entityManager.persistAndFlush(token1user2);


        //when
        repository.deleteByUserId(user1);
        var found = repository.findAll();

        //then
        assertThat(found).containsExactly(token1user2);
    }

    @Test
    void existsByUserId() {
        var user1 = "user1";

        var token1user1 = new TokenPasswordEntity(LocalDateTime.now());
        token1user1.setUserId(user1);
        entityManager.persistAndFlush(token1user1);

        //when
        var responseTrue = repository.existsByUserId(user1);
        var responseFalse = repository.existsByUserId("otherUser");

        //then
        assertTrue(responseTrue);
        assertFalse(responseFalse);
    }
}