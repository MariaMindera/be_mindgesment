package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TransactionRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents some methods for TransactionEntity:
 * - CRUD
 * - Find all transactions by user (findAllByUser)
 * - Find all transactions by user and a specific category (findAllByCategoryAndUser)
 * - Find all transactions by user and a specific type (findAllByTypeAndUser)
 * - Find all transactions by user and in specific date (findAllByDateAndUser)
 * - Find all wish's transactions (findAllByWish)
 * - Get total transactions by user (countByUser)
 * - Get total transactions per user and a specific category (countByCategoryAndUser)
 * - Get total transactions per user and a specific type (countByTypeAndUser)
 * - Get total transactions per user and a specific date (countByDateAndUser)
 * - Get the sum of the value of all transactions of this type is income between a specific date per user  (sumIncomeInThisMonthByUser)
 * - Get the sum of the value of all transactions of this type is expense between a specific date per user  (sumExpenseInThisMonthByUser)
 * - Get the sum of the value of all such transactions as income in the last half year per user (totalIncomeInHalfAYearByUser)
 * - Get the sum of the value of all such transactions as expense in the last half year per user (totalExpenseInHalfAYearByUser)
 * - Get a user's first transaction date (dateOfFirstTransactionByUser)
 * - Get the sum of the value of all transactions of this type as income per user (sumIncomeByUser)
 * - Get the sum of the value of all transactions of this type as expense per user (sumExpenseByUser)
 * - Delete by user id (deleteByUserId)
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    List<TransactionEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<TransactionEntity> findAllByCategoryAndUser(CategoryEntity category, UserEntity user, Pageable pageable);

    List<TransactionEntity> findAllByTypeAndUser(TransactionTypeEntity transactionType, UserEntity user, Pageable pageable);

    List<TransactionEntity> findAllByDateAndUser(LocalDate date, UserEntity user, Pageable pageable);

    List<TransactionEntity> findAllByWish(WishEntity wishEntity, Pageable pageable);

    Long countByUser(UserEntity user);

    Long countByCategoryAndUser(CategoryEntity category, UserEntity user);

    Long countByTypeAndUser(TransactionTypeEntity transactionType, UserEntity user);

    Long countByDateAndUser(LocalDate date, UserEntity user);

    @Query(value = "SELECT sum(t.amount) FROM TransactionEntity t WHERE t.user = ?1 AND t.type='INCOME' AND t.date BETWEEN ?2 AND ?3")
    Double sumIncomeInThisMonthByUser(UserEntity user, LocalDate initialDate, LocalDate lastDate);

    @Query(value = "SELECT sum(t.amount) FROM TransactionEntity t WHERE t.user = ?1 AND t.type='EXPENSE' AND t.date BETWEEN ?2 AND ?3")
    Double sumExpenseInThisMonthByUser(UserEntity user, LocalDate initialDate, LocalDate lastDate);

    @Query(value = "SELECT sum(t.amount) FROM transactions t WHERE t.user_id = ?1 AND t.type='INCOME' AND t.date BETWEEN CURRENT_DATE - INTERVAL '7 months' AND CURRENT_DATE - INTERVAL '1 months';", nativeQuery = true)
    Double totalIncomeInHalfAYearByUser(String userId);

    @Query(value = "SELECT sum(t.amount) FROM transactions t WHERE t.user_id = ?1 AND t.type='EXPENSE' AND t.date BETWEEN CURRENT_DATE - INTERVAL '7 months' AND CURRENT_DATE - INTERVAL '1 months';", nativeQuery = true)
    Double totalExpenseInHalfAYearByUser(String userId);

    @Query(value = "SELECT MIN(t.date) FROM transactions t WHERE t.user_id = ?1", nativeQuery = true)
    LocalDate dateOfFirstTransactionByUser(String userId);

    @Query(value = "SELECT sum(t.amount) FROM TransactionEntity t WHERE t.user = ?1 AND t.type='INCOME'")
    Double sumIncomeByUser(UserEntity user);

    @Query(value = "SELECT sum(t.amount) FROM TransactionEntity t WHERE t.user = ?1 AND t.type='EXPENSE'")
    Double sumExpenseByUser(UserEntity user);

    @Modifying
    @Query("DELETE FROM TransactionEntity t WHERE t.id=:id")
    void deleteById(@NotNull @Param("id") String id);
}
