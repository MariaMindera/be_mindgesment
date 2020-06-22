package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.entities.WishEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * WishRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents some methods for WishEntity:
 * - CRUD
 * - Find all user's wishes (findAllByUser)
 * - Find all completed user's transactions (findAllByCompletedAndUser)
 * - Get total user wishes (countByUser)
 * - Delete by user id (deleteByUserId)
 */
@Repository
public interface WishRepository extends JpaRepository<WishEntity, String> {

    @Query(value = "SELECT * FROM wishes w WHERE w.user_id = ?1 AND w.completed = false", nativeQuery = true)
    List<WishEntity> findAllByUser(String userId);

    List<WishEntity> findAllByCompletedAndUser(boolean completed, UserEntity user, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM wishes w WHERE w.user_id = ?1 AND w.completed = false", nativeQuery = true)
    Long countByUser(String userId);

    @Modifying
    @Query("DELETE FROM WishEntity w WHERE w.id=:id")
    void deleteById(@NotNull @Param("id") String id);
}