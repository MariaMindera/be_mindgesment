package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CoinEntity;
import com.mindera.school.mindgesment.data.entities.TransactionEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.exceptions.AddError;
import com.mindera.school.mindgesment.exceptions.DeleteError;
import com.mindera.school.mindgesment.exceptions.NotExits;
import com.mindera.school.mindgesment.http.models.Transaction;
import com.mindera.school.mindgesment.services.TransactionUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransactionServiceImplTest {

    @Mock
    private TransactionUtilsService transactionUtils;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MapperFacade mapper;

    @InjectMocks
    private TransactionServiceImpl subject;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void add() {
        //given
        var username = "username";
        var user = new UserEntity();
        var transaction = new Transaction();
        var transactionEntity = new TransactionEntity();
        transactionEntity.setUser(user);

        //when
        doNothing().when(transactionUtils)
                .verifyCategoryWithType(eq(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transactionEntity);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(transactionRepository.save(eq(transactionEntity)))
                .thenReturn(transactionEntity);
        when(mapper.map(eq(transactionEntity), eq(Transaction.class)))
                .thenReturn(transaction);

        var response = subject.add(transaction, username);

        //then
        assertEquals(transaction, response);

        verify(transactionUtils, times(1))
                .verifyCategoryWithType(eq(transaction));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .save(eq(transactionEntity));
        verify(mapper, times(1))
                .map(eq(transactionEntity), eq(Transaction.class));
    }

    @Test
    void addAddError() {
        //given
        var username = "username";
        var user = new UserEntity();
        var transaction = new Transaction();
        var transactionEntity = new TransactionEntity();
        transactionEntity.setUser(user);

        //when
        doNothing().when(transactionUtils)
                .verifyCategoryWithType(eq(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transactionEntity);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(transactionRepository.save(eq(transactionEntity)))
                .thenThrow(DataIntegrityViolationException.class);

        //then
        var response = assertThrows(AddError.class, () -> subject.add(transaction, username));
        assertEquals("Invalid add Transaction.", response.getMessage());

        verify(transactionUtils, times(1))
                .verifyCategoryWithType(eq(transaction));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .save(eq(transactionEntity));
    }

    @Test
    void edit() {
        //given
        var username = "username";
        var transactionId = "transactionId";

        var instantCreated = Instant.now();

        var user = new UserEntity();
        var transaction = new Transaction();

        var transactionEntity = new TransactionEntity();
        transactionEntity.setUser(user);
        transactionEntity.setId(transactionId);

        var transactionDatabase = new TransactionEntity();
        transactionDatabase.setUser(user);
        transactionDatabase.setCreatedIn(instantCreated);

        //when
        doNothing().when(transactionUtils)
                .verifyCategoryWithType(eq(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transactionEntity);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(transactionRepository.findById(eq(transactionId)))
                .thenReturn(Optional.of(transactionDatabase));
        when(mapper.map(eq(transactionDatabase), eq(TransactionEntity.class)))
                .thenReturn(transactionDatabase);
        when(transactionRepository.save(eq(transactionEntity)))
                .thenReturn(transactionEntity);
        when(mapper.map(eq(transactionEntity), eq(Transaction.class)))
                .thenReturn(transaction);

        subject.edit(transactionId, transaction, username);

        //then
        verify(transactionUtils, times(1))
                .verifyCategoryWithType(eq(transaction));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .findById(eq(transactionId));
        verify(mapper, times(1))
                .map(eq(transactionDatabase), eq(TransactionEntity.class));
        verify(transactionRepository, times(1))
                .save(eq(transactionEntity));
        verify(mapper, times(1))
                .map(eq(transactionEntity), eq(Transaction.class));
    }

    @Test
    void editNotExists() {
        //given
        var username = "username";
        var transactionId = "transactionId";

        var instantCreated = Instant.now();

        var user = new UserEntity();
        var transaction = new Transaction();

        var transactionEntity = new TransactionEntity();
        transactionEntity.setUser(user);
        transactionEntity.setId(transactionId);

        var transactionDatabase = new TransactionEntity();
        transactionDatabase.setCreatedIn(instantCreated);
        transactionDatabase.setUser(new UserEntity("email", "***", "username", CoinEntity.EUR));

        //when
        doNothing().when(transactionUtils)
                .verifyCategoryWithType(eq(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transactionEntity);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        when(transactionRepository.findById(eq(transactionId)))
                .thenReturn(Optional.of(transactionDatabase));
        when(mapper.map(eq(transactionDatabase), eq(TransactionEntity.class)))
                .thenReturn(transactionDatabase);

        //then
        var response = assertThrows(NotExits.class, () -> subject.edit(transactionId, transaction, username));
        assertEquals(String.format("Transaction not found with this id=%s.", transactionId), response.getMessage());

        verify(transactionUtils, times(1))
                .verifyCategoryWithType(eq(transaction));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .findById(eq(transactionId));
        verify(mapper, times(1))
                .map(eq(transactionDatabase), eq(TransactionEntity.class));
    }

    @Test
    void delete() {
        //given
        var transactionId = "transactionId";
        var username = "username";

        var user = new UserEntity();
        var transaction = new TransactionEntity();
        transaction.setUser(user);

        //when
        when(transactionRepository.findById(eq(transactionId)))
                .thenReturn(Optional.of(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transaction);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);

        subject.delete(transactionId, username);

        //then
        verify(transactionRepository, times(1))
                .findById(eq(transactionId));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .deleteById(eq(transactionId));
    }

    @Test
    void deleteDeleteError() {
        //given
        var transactionId = "transactionId";
        var username = "username";

        var user = new UserEntity();
        var transaction = new TransactionEntity();
        transaction.setUser(user);

        //when
        when(transactionRepository.findById(eq(transactionId)))
                .thenReturn(Optional.of(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transaction);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(user);
        doThrow(DataIntegrityViolationException.class)
                .when(transactionRepository)
                .deleteById(eq(transactionId));

        //then
        var response = assertThrows(DeleteError.class, () -> subject.delete(transactionId, username));

        assertEquals("Invalid delete Transaction.", response.getMessage());

        verify(transactionRepository, times(1))
                .findById(eq(transactionId));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(1))
                .deleteById(eq(transactionId));
    }

    @Test
    void deleteNotExist() {
        //given
        var transactionId = "transactionId";
        var username = "username";

        var user = new UserEntity();
        user.setUsername("user");

        var transaction = new TransactionEntity();
        transaction.setUser(user);

        //when
        when(transactionRepository.findById(eq(transactionId)))
                .thenReturn(Optional.of(transaction));
        when(mapper.map(eq(transaction), eq(TransactionEntity.class)))
                .thenReturn(transaction);
        when(transactionUtils.findUserEntity(eq(username)))
                .thenReturn(new UserEntity());

        //then
        var response = assertThrows(NotExits.class, () -> subject.delete(transactionId, username));

        assertEquals(String.format("Transaction not found with this id=%s.", transactionId), response.getMessage());

        verify(transactionRepository, times(1))
                .findById(eq(transactionId));
        verify(mapper, times(1))
                .map(eq(transaction), eq(TransactionEntity.class));
        verify(transactionUtils, times(1))
                .findUserEntity(eq(username));
        verify(transactionRepository, times(0))
                .deleteById(anyString());
    }

    @Test
    void testDelete() {
        //given
        var transactionId = "transactionId";

        //when
        subject.delete(transactionId);

        //then
        verify(transactionRepository, times(1))
                .deleteById(eq(transactionId));
    }
}