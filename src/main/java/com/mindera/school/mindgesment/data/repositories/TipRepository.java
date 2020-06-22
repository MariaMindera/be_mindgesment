package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.TipEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * TipRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents all CRUD methods and delete by id for TipEntity.
 */
@Repository
public interface TipRepository extends JpaRepository<TipEntity, String> {

    @Modifying
    @Query("DELETE FROM TipEntity t WHERE t.id=:id")
    void deleteById(@NotNull @Param("id") String id);
}