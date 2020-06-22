package com.mindera.school.mindgesment.utils;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.RoleEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.RoleRepository;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        if (alreadySetup) return;

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        var adminRole = roleRepository.findByName("ROLE_ADMIN");
        var user = new UserEntity();
        user.setUsername("Admin");
        user.setEmail("admin@admin.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setMonthlyPeriod(2);
        user.setCoin(CoinEntity.EUR);
        user.setRoles(Set.of(adminRole));

        createAdmIfNotFound(user);

        alreadySetup = true;
    }

    @Transactional
    private void createAdmIfNotFound(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            userRepository.save(user);
        }
    }

    @Transactional
    private void createRoleIfNotFound(String name) {
        var role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.save(role);
        }
    }
}
