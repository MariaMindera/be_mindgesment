package com.mindera.school.mindgesment.data.repository;

import com.mindera.school.mindgesment.data.entities.*;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository repository;

    @Test
    void findAllByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var transaction1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        var transaction2 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        var transaction3 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2));

        //when
        var found = repository.findAllByUser(user1, Pageable.unpaged());

        //then
        assertThat(found).containsExactly(transaction1, transaction2, transaction3);
    }

    @Test
    void findAllByCategoryAndUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var salary1User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        var salary2User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        var food1User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        var food1User2 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2));

        //when
        var salaryUser1 = repository.findAllByCategoryAndUser(CategoryEntity.SALARY, user1, Pageable.unpaged());
        var foodUser1 = repository.findAllByCategoryAndUser(CategoryEntity.FOOD_DRINK, user1, Pageable.unpaged());
        var foodUser2 = repository.findAllByCategoryAndUser(CategoryEntity.FOOD_DRINK, user2, Pageable.unpaged());

        //then
        assertThat(salaryUser1).containsExactly(salary1User1, salary2User1);
        assertThat(foodUser1).containsExactly(food1User1);
        assertThat(foodUser2).containsExactly(food1User2);
    }

    @Test
    void findAllByTypeAndUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var income1User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        var income2User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        var expense1User1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        var income1User2 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.INCOME, user2));

        //when
        var incomeUser1 = repository.findAllByTypeAndUser(TransactionTypeEntity.INCOME, user1, Pageable.unpaged());
        var expenseUser1 = repository.findAllByTypeAndUser(TransactionTypeEntity.EXPENSE, user1, Pageable.unpaged());
        var incomeUser2 = repository.findAllByTypeAndUser(TransactionTypeEntity.INCOME, user2, Pageable.unpaged());

        //then
        assertThat(incomeUser1).containsExactly(income1User1, income2User1);
        assertThat(expenseUser1).containsExactly(expense1User1);
        assertThat(incomeUser2).containsExactly(income1User2);
    }

    @Test
    void findAllByDateAndUser() {
        //given
        var dateSubject = LocalDate.of(2000, 12, 1);

        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var transaction1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction1.setDate(dateSubject);
        entityManager.persistAndFlush(transaction1);

        var transaction2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction2.setDate(dateSubject.minusMonths(1));
        entityManager.persistAndFlush(transaction2);

        var transactionUser2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user2);
        transactionUser2.setDate(dateSubject);
        entityManager.persistAndFlush(transactionUser2);

        //when
        var found1User1 = repository.findAllByDateAndUser(dateSubject, user1, Pageable.unpaged());
        var found2User1 = repository.findAllByDateAndUser(dateSubject.minusMonths(1), user1, Pageable.unpaged());
        var foundUser2 = repository.findAllByDateAndUser(dateSubject, user2, Pageable.unpaged());

        //then
        assertThat(found1User1).containsExactly(transaction1);
        assertThat(found2User1).containsExactly(transaction2);
        assertThat(foundUser2).containsExactly(transactionUser2);
    }

    @Test
    void findAllByWish() {
        //given
        var wish1 = entityManager.persistAndFlush(new WishEntity("wish1", 100L, 10L, YearMonth.of(2030, 3), null));
        var wish2 = entityManager.persistAndFlush(new WishEntity("wish1", 100L, 10L, YearMonth.of(2030, 3), null));

        var transaction1wish1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, null);
        transaction1wish1.setWish(wish1);
        entityManager.persistAndFlush(transaction1wish1);

        var transaction2wish1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, null);
        transaction2wish1.setWish(wish1);
        entityManager.persistAndFlush(transaction2wish1);

        var transaction1wish2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, null);
        transaction1wish2.setWish(wish2);
        entityManager.persistAndFlush(transaction1wish2);

        //when
        var foundWish1 = repository.findAllByWish(wish1, Pageable.unpaged());
        var foundWish2 = repository.findAllByWish(wish2, Pageable.unpaged());

        //then
        assertThat(foundWish1).containsExactly(transaction1wish1, transaction2wish1);
        assertThat(foundWish2).containsExactly(transaction1wish2);
    }

    @Test
    void countByCategoryAndUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2));

        //when
        var salaryUser1 = repository.countByCategoryAndUser(CategoryEntity.SALARY, user1);
        var foodUser1 = repository.countByCategoryAndUser(CategoryEntity.FOOD_DRINK, user1);
        var foodUser2 = repository.countByCategoryAndUser(CategoryEntity.FOOD_DRINK, user2);

        //then
        assertThat(salaryUser1).isEqualTo(2);
        assertThat(foodUser1).isEqualTo(1);
        assertThat(foodUser2).isEqualTo(1);
    }

    @Test
    void countByTypeAndUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.INCOME, user2));

        //when
        var incomeUser1 = repository.countByTypeAndUser(TransactionTypeEntity.INCOME, user1);
        var expenseUser1 = repository.countByTypeAndUser(TransactionTypeEntity.EXPENSE, user1);
        var incomeUser2 = repository.countByTypeAndUser(TransactionTypeEntity.INCOME, user2);

        //then
        assertThat(incomeUser1).isEqualTo(2);
        assertThat(expenseUser1).isEqualTo(1);
        assertThat(incomeUser2).isEqualTo(1);
    }

    @Test
    void countByDateAndUser() {
        //given
        var dateSubject = LocalDate.of(2000, 12, 1);

        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var transaction1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction1.setDate(dateSubject);
        entityManager.persistAndFlush(transaction1);

        var transaction2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction2.setDate(dateSubject);
        entityManager.persistAndFlush(transaction2);

        var transactionUser2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user2);
        transactionUser2.setDate(dateSubject);
        entityManager.persistAndFlush(transactionUser2);

        //when
        var foundUser1 = repository.countByDateAndUser(dateSubject, user1);
        var foundUser2 = repository.countByDateAndUser(dateSubject, user2);

        //then
        assertThat(foundUser1).isEqualTo(2);
        assertThat(foundUser2).isEqualTo(1);
    }

    @Test
    void countByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2));

        //when
        var foundUser1 = repository.countByUser(user1);
        var foundUser2 = repository.countByUser(user2);

        //then
        assertThat(foundUser1).isEqualTo(3);
        assertThat(foundUser2).isEqualTo(1);
    }

    @Test
    void sumIncomeInThisMonthByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var income1User1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        income1User1.setDate(LocalDate.now());
        entityManager.persistAndFlush(income1User1);

        var income2User1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        income2User1.setDate(LocalDate.now());
        entityManager.persistAndFlush(income2User1);

        var expenseUser1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1);
        expenseUser1.setDate(LocalDate.now());
        entityManager.persistAndFlush(expenseUser1);

        var incomeUser2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user2);
        incomeUser2.setDate(LocalDate.now());
        entityManager.persistAndFlush(incomeUser2);

        //when
        var foundUser1 = repository.sumIncomeInThisMonthByUser(user1, LocalDate.now().minusMonths(1), LocalDate.now());
        var foundUser2 = repository.sumIncomeInThisMonthByUser(user2, LocalDate.now().minusMonths(1), LocalDate.now());

        //then
        assertThat(foundUser1).isEqualTo(24);
        assertThat(foundUser2).isEqualTo(12);
    }

    @Test
    void sumExpenseInThisMonthByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var expense1User1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1);
        expense1User1.setDate(LocalDate.now());
        entityManager.persistAndFlush(expense1User1);

        var expense2User1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1);
        expense2User1.setDate(LocalDate.now());
        entityManager.persistAndFlush(expense2User1);

        var incomeUser1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        incomeUser1.setDate(LocalDate.now());
        entityManager.persistAndFlush(incomeUser1);

        var expenseUser2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2);
        expenseUser2.setDate(LocalDate.now());
        entityManager.persistAndFlush(expenseUser2);

        //when
        var foundUser1 = repository.sumExpenseInThisMonthByUser(user1, LocalDate.now().minusMonths(1), LocalDate.now());
        var foundUser2 = repository.sumExpenseInThisMonthByUser(user2, LocalDate.now().minusMonths(1), LocalDate.now());

        //then
        assertThat(foundUser1).isEqualTo(24);
        assertThat(foundUser2).isEqualTo(12);
    }

    @Test
    void dateOfFirstTransactionByUser() {
        //given
        var dateSubjectUser1 = LocalDate.of(2000, 12, 1);
        var dateSubjectUser2 = LocalDate.of(2000, 10, 1);

        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        var transaction1 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction1.setDate(dateSubjectUser1);
        entityManager.persistAndFlush(transaction1);

        var transaction2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1);
        transaction2.setDate(LocalDate.now());
        entityManager.persistAndFlush(transaction2);

        var transactionUser2 = new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user2);
        transactionUser2.setDate(dateSubjectUser2);
        entityManager.persistAndFlush(transactionUser2);

        //when
        var foundUser1 = repository.dateOfFirstTransactionByUser(user1.getId());
        var foundUser2 = repository.dateOfFirstTransactionByUser(user2.getId());

        //then
        assertThat(foundUser1).isEqualTo(dateSubjectUser1);
        assertThat(foundUser2).isEqualTo(dateSubjectUser2);
    }

    @Test
    void sumIncomeByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user2));

        //when
        var foundUser1 = repository.sumIncomeByUser(user1);
        var foundUser2 = repository.sumIncomeByUser(user2);

        //then
        assertThat(foundUser1).isEqualTo(24);
        assertThat(foundUser2).isEqualTo(12);
    }

    @Test
    void sumExpenseByUser() {
        //given
        var user1 = entityManager.persistAndFlush(new UserEntity("a@a.com", "12345678", "a", CoinEntity.EUR));
        var user2 = entityManager.persistAndFlush(new UserEntity("b@b.com", "12345678", "b", CoinEntity.EUR));

        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user1));
        entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.FOOD_DRINK, TransactionTypeEntity.EXPENSE, user2));

        //when
        var foundUser1 = repository.sumExpenseByUser(user1);
        var foundUser2 = repository.sumExpenseByUser(user2);

        //then
        assertThat(foundUser1).isEqualTo(24);
        assertThat(foundUser2).isEqualTo(12);
    }

    @Test
    void deleteById() {
        //given
        var transaction1 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, null));
        var transaction2 = entityManager.persistAndFlush(new TransactionEntity("test1", 12.00, LocalDate.now(), CategoryEntity.SALARY, TransactionTypeEntity.INCOME, null));

        //when
        repository.deleteById(transaction1.getId());
        var found = repository.findAll();

        //then
        assertThat(found).containsExactly(transaction2);
    }
}