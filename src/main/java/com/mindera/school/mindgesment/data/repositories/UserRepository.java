package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents some methods for UserEntity:
 * - CRUD
 * - Find a user by username (findByUsername)
 * - Find a user by email (findByEmail)
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
