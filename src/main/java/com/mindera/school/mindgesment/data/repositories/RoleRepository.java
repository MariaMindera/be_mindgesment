package com.mindera.school.mindgesment.data.repositories;

import com.mindera.school.mindgesment.data.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository interface extends JpaRepository
 * and exists to have generic CRUD methods and own methods.
 * <p>
 * This interface presents all CRUD methods and find by name for RoleEntity.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    RoleEntity findByName(String name);
}