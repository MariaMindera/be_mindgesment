package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.TipEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.TipRepository;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.exceptions.*;
import com.mindera.school.mindgesment.http.models.Tip;
import com.mindera.school.mindgesment.services.TipService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    @Autowired
    public TipServiceImpl(TipRepository tipRepository, UserRepository userRepository, MapperFacade mapper) {
        this.tipRepository = tipRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(String username, Tip tip) {
        try {
            tipRepository.save(mapper.map(tip, TipEntity.class));
        } catch (DataIntegrityViolationException e) {
            throw new AddError("Tip", e);
        }
    }

    @Override
    public Tip edit(String username, String tipId, Tip editTip) {
        if (! tipRepository.existsById(tipId)) {
            throw new NotExits("Tip", tipId);
        }

        var tip = mapper.map(editTip, TipEntity.class);
        tip.setId(tipId);

        try {
            return mapper.map(tipRepository.save(tip), Tip.class);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("Tip", e);
        }
    }

    @Override
    public void delete(String username, String tipId) {
        if (! tipRepository.existsById(tipId)) {
            throw new NotExits("Tip", tipId);
        }

        try {
            delete(tipId);
        } catch (DataIntegrityViolationException e) {
            throw new EditError("Tip", e);
        }
    }

    @Override
    public void validateUserAdmin(String username) {
        if (
                userRepository.findByUsername(username)
                        .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                        .orElseThrow(() -> new UserNotExists(username))
                        .getRoles()
                        .stream()
                        .noneMatch(roleEntity -> roleEntity.getName().equals("ROLE_ADMIN"))
        ) {
            throw new Unauthorized();
        }
    }

    @Transactional
    @Override
    public void delete(String tipId) {
        tipRepository.deleteById(tipId);
    }
}
