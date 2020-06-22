package com.mindera.school.mindgesment.utils;

import com.mindera.school.mindgesment.data.entities.TokenPasswordEntity;
import com.mindera.school.mindgesment.data.repositories.TokenPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class TokenPasswordGenerator {

    private final TokenPasswordRepository repository;

    @Autowired
    public TokenPasswordGenerator(TokenPasswordRepository repository) {
        this.repository = repository;
    }

    public String create(String userId) {
        if (repository.existsByUserId(userId)) {
            repository.deleteByUserId(userId);
        }

        var token = new TokenPasswordEntity();
        token.setCreatedAt(LocalDateTime.now());
        token.setUserId(userId);

        try {
            return repository.save(token).getId();
        } catch (DataIntegrityViolationException e) {
            return null;
        }
    }

    public String findToken(final String token) {
        return repository.findById(token)
                .map(TokenPasswordEntity::getUserId)
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
