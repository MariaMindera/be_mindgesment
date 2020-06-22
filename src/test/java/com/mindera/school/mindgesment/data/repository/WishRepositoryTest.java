package com.mindera.school.mindgesment.data.repository;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class WishRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishRepository wishRepository;

    @Test
    void findAllByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new WishEntity("wish1", 100L, 10L, YearMonth.of(2030, 3), user1));
        entityManager.persistAndFlush(new WishEntity("wish2", 100L, 10L, YearMonth.of(2030, 3), user1));
        entityManager.persistAndFlush(new WishEntity("wish3", 100L, 10L, YearMonth.of(2030, 3), user1));

        var wish1User2 = entityManager.persistAndFlush(new WishEntity("test1", 100L, 10L, YearMonth.of(2030, 3), user2));
        var wish2User2 = entityManager.persistAndFlush(new WishEntity("test2", 100L, 10L, YearMonth.of(2030, 3), user2));
        var wish3User2 = entityManager.persistAndFlush(new WishEntity("test3", 100L, 10L, YearMonth.of(2030, 3), user2));

        //when
        var found = wishRepository.findAllByUser(user2.getId());

        //then
        assertThat(found).containsExactly(wish1User2, wish2User2, wish3User2);
    }

    @Test
    void findAllByCompletedAndUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var completed1User1 = new WishEntity("completed1User1", 100L, 10L, YearMonth.of(2030, 3), user1);
        completed1User1.setCompleted(true);
        entityManager.persistAndFlush(completed1User1);

        var completed2User1 = new WishEntity("completed2User1", 100L, 10L, YearMonth.of(2030, 3), user1);
        completed2User1.setCompleted(true);
        entityManager.persistAndFlush(completed2User1);

        var wish1User1 = entityManager.persistAndFlush(new WishEntity("wish2", 100L, 10L, YearMonth.of(2030, 3), user1));

        var completedUser2 = new WishEntity("completedUser2", 100L, 10L, YearMonth.of(2030, 3), user2);
        completedUser2.setCompleted(true);
        entityManager.persistAndFlush(completedUser2);

        //when
        var foundCompletedUser1 = wishRepository.findAllByCompletedAndUser(true, user1, Pageable.unpaged());
        var foundNonCompletedUser1 = wishRepository.findAllByCompletedAndUser(false, user1, Pageable.unpaged());

        var foundCompletedUser2 = wishRepository.findAllByCompletedAndUser(true, user2, Pageable.unpaged());
        var foundNonCompletedUser2 = wishRepository.findAllByCompletedAndUser(false, user2, Pageable.unpaged());

        //then
        assertThat(foundCompletedUser1).containsExactly(completed1User1, completed2User1);
        assertThat(foundNonCompletedUser1).containsExactly(wish1User1);

        assertThat(foundCompletedUser2).containsExactly(completedUser2);
        assertThat(foundNonCompletedUser2).isEmpty();
    }

    @Test
    void countByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));

        entityManager.persistAndFlush(new WishEntity("wish1", 100L, 10L, YearMonth.of(2030, 3), user1));
        entityManager.persistAndFlush(new WishEntity("wish2", 100L, 10L, YearMonth.of(2030, 3), user1));
        entityManager.persistAndFlush(new WishEntity("wish3", 100L, 10L, YearMonth.of(2030, 3), user1));
        var completed1User1 = new WishEntity("completed1User1", 100L, 10L, YearMonth.of(2030, 3), user1);
        completed1User1.setCompleted(true);
        entityManager.persistAndFlush(completed1User1);

        //when
        var found = wishRepository.countByUser(user1.getId());

        //then
        assertThat(found).isEqualTo(3L);
    }

    @Test
    void deleteById() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var wish1User1 = entityManager.persistAndFlush(new WishEntity("test1", 100L, 10L, YearMonth.of(2030, 3), user1));
        var wish2User1 = entityManager.persistAndFlush(new WishEntity("test2", 100L, 10L, YearMonth.of(2030, 3), user1));

        //when
        wishRepository.deleteById(wish1User1.getId());
        var found = wishRepository.findAll();

        //then
        assertThat(found).containsExactly(wish2User1);
    }
}