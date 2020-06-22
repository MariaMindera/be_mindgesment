package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.TransactionEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.exceptions.AddError;
import com.mindera.school.mindgesment.exceptions.DeleteError;
import com.mindera.school.mindgesment.exceptions.EditError;
import com.mindera.school.mindgesment.exceptions.NotExits;
import com.mindera.school.mindgesment.http.models.Transaction;
import com.mindera.school.mindgesment.services.TransactionService;
import com.mindera.school.mindgesment.services.TransactionUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionUtilsService transactionUtils;

    private final TransactionRepository transactionRepository;

    private final MapperFacade mapper;

    @Autowired
    public TransactionServiceImpl(TransactionUtilsService transactionUtils, TransactionRepository transactionRepository, MapperFacade mapper) {
        this.transactionUtils = transactionUtils;
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public Transaction add(final Transaction newTransaction, final String username) {
        transactionUtils.verifyCategoryWithType(newTransaction);

        var transaction = mapper.map(newTransaction, TransactionEntity.class);
        transaction.setUser(transactionUtils.findUserEntity(username));

        try {
            return mapper.map(
                    transactionRepository.save(transaction),
                    Transaction.class
            );
        } catch (DataIntegrityViolationException c) {
            throw new AddError("Transaction", c);
        }
    }

    @Override
    public Transaction edit(final String transactionId, final Transaction editedTransaction, final String username) {
        transactionUtils.verifyCategoryWithType(editedTransaction);

        var transaction = mapper.map(editedTransaction, TransactionEntity.class);
        transaction.setId(transactionId);
        transaction.setUser(transactionUtils.findUserEntity(username));

        var transactionDatabase = transactionRepository.findById(transactionId)
                .map(transactionEntity -> mapper.map(transactionEntity, TransactionEntity.class))
                .orElseThrow(() -> new NotExits("Transaction", transactionId));

        if (! transactionDatabase.getUser()
                .equals(transaction.getUser()))
            throw new NotExits("Transaction", transactionId);

        transaction.setCreatedIn(transactionDatabase.getCreatedIn());

        try {
            return mapper.map(transactionRepository.save(transaction), Transaction.class);
        } catch (DataIntegrityViolationException c) {
            throw new EditError("Transaction", c);
        }
    }

    @Override
    public void delete(final String transactionId, final String username) {
        if (! transactionRepository.findById(transactionId)
                .map(transactionEntity -> mapper.map(transactionEntity, TransactionEntity.class))
                .orElseThrow(() -> new NotExits("Transaction", transactionId))
                .getUser()
                .equals(transactionUtils.findUserEntity(username)))
            throw new NotExits("Transaction", transactionId);

        try {
            delete(transactionId);
        } catch (DataIntegrityViolationException c) {
            throw new DeleteError("Transaction", c);
        }
    }

    @Override
    @Transactional
    public void delete(String transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
