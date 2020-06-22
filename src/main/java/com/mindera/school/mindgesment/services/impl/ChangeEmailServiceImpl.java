package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.InvalidToken;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.http.models.EmailChange;
import com.mindera.school.mindgesment.services.ChangeEmailService;
import com.mindera.school.mindgesment.utils.EmailSender;
import com.mindera.school.mindgesment.utils.TokenEmailGenerator;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChangeEmailServiceImpl implements ChangeEmailService {

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    private final TokenEmailGenerator tokenGenerator;

    private final EmailSender emailSender;

    @Autowired
    public ChangeEmailServiceImpl(UserRepository userRepository, MapperFacade mapper, TokenEmailGenerator tokenGenerator, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.tokenGenerator = tokenGenerator;
        this.emailSender = emailSender;
    }

    @Override
    public void requestChangeEmail(String username) {
        var user = userRepository.findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));

        var token = tokenGenerator.create(user.getId(), null);

        if (token != null) {
            emailSender.sendChangeEmail(user.getUsername(), user.getEmail(), token);
        }
    }

    @Override
    public void changeEmail(EmailChange emailChange) {
        var token = tokenGenerator.findToken(emailChange.getToken());

        if (token == null) {
            throw new InvalidToken();
        }

        var user = userRepository.findById(token.getUserId())
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElse(null);

        var newToken = tokenGenerator.create(token.getUserId(), emailChange.getEmail());

        if (newToken != null && user != null) {
            emailSender.sendConfirmNewEmail(user.getUsername(), emailChange.getEmail(), newToken);
            tokenGenerator.deleteToken(token.getId());
        }
    }

    @Override
    public void confirmChangeEmail(String token) {
        var tokenEntity = tokenGenerator.findToken(token);

        if (tokenEntity == null) {
            throw new InvalidToken();
        }

        var user = userRepository.findById(tokenEntity.getUserId())
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElse(null);

        Objects.requireNonNull(user)
                .setEmail(tokenEntity.getNewEmail());

        try {
            userRepository.save(user);
            tokenGenerator.deleteToken(token);
        } catch (DataIntegrityViolationException ignored) {
        }
    }
}
