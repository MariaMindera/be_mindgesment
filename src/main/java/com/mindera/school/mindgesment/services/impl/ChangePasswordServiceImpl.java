package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidToken;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.PasswordReset;
import com.mindera.school.mindgesment.services.ChangePasswordService;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenPasswordGenerator;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    private final TokenPasswordGenerator tokenGenerator;

    private final EmailSender emailSender;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ChangePasswordServiceImpl(UserRepository userRepository, MapperFacade mapper, TokenPasswordGenerator tokenGenerator, EmailSender emailSender, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.tokenGenerator = tokenGenerator;
        this.emailSender = emailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void requestChangePassword(String username) {
        var user = userRepository.findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));

        var token = tokenGenerator.create(user.getId());

        if (token != null) {
            emailSender.sendChangePassword(user.getUsername(), user.getEmail(), token);
        }
    }

    @Override
    public void changePassword(PasswordReset passwordReset) {
        var userId = tokenGenerator.findToken(passwordReset.getToken());

        if (userId == null) {
            throw new InvalidToken();
        }

        var user = userRepository.findById(userId)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElse(null);
        Objects.requireNonNull(user).setPassword(bCryptPasswordEncoder.encode(passwordReset.getPassword()));

        try {
            userRepository.save(user);
            tokenGenerator.deleteToken(passwordReset.getToken());
        } catch (DataIntegrityViolationException ignored) {
        }
    }
}
