package com.mindera.school.mindgesment.data.repository;

import com.mindera.school.mindgesment.data.entities.RoleEntity;
import com.mindera.school.mindgesment.data.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName() {
        // given
        var roleAdmin = new RoleEntity("ROLE_ADMIN");
        entityManager.persistAndFlush(roleAdmin);

        // when
        var found = roleRepository.findByName(roleAdmin.getName());

        // then
        assertThat(found.getName())
                .isEqualTo(roleAdmin.getName());
    }
}