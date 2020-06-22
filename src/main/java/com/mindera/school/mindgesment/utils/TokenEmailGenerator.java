package com.mindera.school.mindgesment.utils;

import com.mindera.school.mindgesment.data.entities.TokenEmailEntity;
import com.mindera.school.mindgesment.data.repositories.TokenEmailRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class TokenEmailGenerator {

    private final TokenEmailRepository repository;

    private final MapperFacade mapper;

    @Autowired
    public TokenEmailGenerator(TokenEmailRepository repository, MapperFacade mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public String create(String userId, String newEmail) {
        if (repository.existsByUserId(userId)) {
            repository.deleteByUserId(userId);
        }

        var token = new TokenEmailEntity();
        token.setCreatedAt(LocalDateTime.now());
        token.setUserId(userId);
        token.setNewEmail(newEmail);

        try {
            return repository.save(token).getId();
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public TokenEmailEntity findToken(final String token) {
        return repository.findById(token)
                .map(tokenEmail -> mapper.map(tokenEmail, TokenEmailEntity.class))
                .orElse(null);
    }

    @Transactional
    public void deleteToken(final String token) {
        repository.deleteById(token);
    }

    @Scheduled(fixedRate = 1000L * 60 * 60)
    public void expire() {
        repository.deleteByCreatedAtIsBefore(LocalDateTime.now().minusHours(24));
    }
}
