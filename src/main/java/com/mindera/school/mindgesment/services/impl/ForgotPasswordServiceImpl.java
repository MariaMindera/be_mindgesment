package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.PasswordForgot;
import com.mindera.school.mindgesment.services.ForgotPasswordService;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenPasswordGenerator;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    private final TokenPasswordGenerator tokenGenerator;

    private final EmailSender emailSender;

    @Autowired
    public ForgotPasswordServiceImpl(UserRepository userRepository, MapperFacade mapper, TokenPasswordGenerator tokenGenerator, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.tokenGenerator = tokenGenerator;
        this.emailSender = emailSender;
    }

    @Override
    public void processForgotPassword(PasswordForgot passwordForgot) {
        var user = userRepository.findByEmail(passwordForgot.getEmail())
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(passwordForgot.getEmail()));

        var token = tokenGenerator.create(user.getId());

        if (token != null) {
            emailSender.sendEmailForgotPassword(user.getUsername(), user.getEmail(), token);
        }
    }
}
