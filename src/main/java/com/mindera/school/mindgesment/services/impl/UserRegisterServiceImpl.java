package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.RoleRepository;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidParameter;
import com.mindera.school.mindgesment.exceptions.RegisterError;
import com.mindera.school.mindgesment.http.models.Coin;
import com.mindera.school.mindgesment.http.models.UserRegister;
import com.mindera.school.mindgesment.services.UserRegisterService;
import com.mindera.school.mindgesment.utils.EmailSender;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MapperFacade mapper;

    private final EmailSender emailSender;

    @Autowired
    public UserRegisterServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MapperFacade mapper, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.emailSender = emailSender;
    }

    @Override
    public void register(final UserRegister user) {
        if (Arrays.stream(Coin.values())
                .noneMatch(coins -> coins.toString()
                        .equals(user.getCoin())))
            throw new InvalidParameter("coin", Arrays.toString(Coin.values()));

        UserEntity userRegister = mapper.map(user, UserEntity.class);

        userRegister.setPassword(
                bCryptPasswordEncoder.encode(
                        user.getPassword()
                ));

        userRegister.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));

        try {
            var register = userRepository.save(userRegister);
            emailSender.sendWelcomeEmail(register.getUsername(), register.getEmail());
        } catch (DataIntegrityViolationException c) {
            throw new RegisterError(c);
        }
    }
}