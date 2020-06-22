package com.mindera.school.mindgesment.data.repository;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        //when
        var found = userRepository.findByUsername("a");

        //then
        assertTrue(found.isPresent());
        assertThat(found.get()).isEqualTo(user1);
    }

    @Test
    void findByEmail() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        //when
        var found = userRepository.findByEmail("a@a.com");

        //then
        assertTrue(found.isPresent());
        assertThat(found.get()).isEqualTo(user1);
    }
}