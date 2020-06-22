package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.TokenEmailEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * TokenEmailRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents some methods for TokenEmailEntity:
 * - CRUD
 * - Delete all that were created before a date (deleteByCreatedAtIsBefore)
 * - Delete by token id (deleteById)
 * - Delete by user id (deleteByUserId)
 * - If a token exists by user id (existsByUserId)
 */
@Repository
public interface TokenEmailRepository extends JpaRepository<TokenEmailEntity, String> {

    @Modifying
    @Query("DELETE FROM TokenEmailEntity t WHERE t.createdAt<:localDateTime")
    void deleteByCreatedAtIsBefore(@Param("localDateTime") LocalDateTime localDateTime);

    @Modifying
    @Query("DELETE FROM TokenEmailEntity t WHERE t.id=:id")
    void deleteById(@NotNull @Param("id") String id);

    @Modifying
    @Query("DELETE FROM TokenEmailEntity t WHERE t.userId=:userId")
    void deleteByUserId(@NotNull @Param("userId") String userId);

    boolean existsByUserId(String userId);
}