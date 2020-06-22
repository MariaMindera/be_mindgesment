package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.CategoryEntity;
import com.mindera.school.mindgesment.data.entities.TransactionTypeEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.exceptions.InvalidPage;
import com.mindera.school.mindgesment.http.models.PaginatedTransactions;
import com.mindera.school.mindgesment.http.models.Transaction;
import com.mindera.school.mindgesment.services.TransactionGetService;
import com.mindera.school.mindgesment.services.TransactionUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class TransactionGetServiceImpl implements TransactionGetService {

    private final TransactionUtilsService transactionUtils;

    private final TransactionRepository transactionRepository;

    private final MapperFacade mapper;

    @Autowired
    public TransactionGetServiceImpl(TransactionUtilsService transactionUtils, TransactionRepository transactionRepository, MapperFacade mapper) {
        this.transactionUtils = transactionUtils;
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public PaginatedTransactions findAllByUser(String username, Integer page) {
        var user = transactionUtils.findUserEntity(username);

        var list = transactionRepository.findAllByUser(
                user,
                PageRequest.of(page, 10, Sort.by("date", "createdIn").descending())
        );

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedTransactions = new PaginatedTransactions();
        paginatedTransactions.setList(
                list.stream()
                        .map(transactionEntity -> mapper.map(transactionEntity, Transaction.class))
                        .collect(Collectors.toList())
        );
        paginatedTransactions.setTotal(transactionRepository.countByUser(user));
        return paginatedTransactions;
    }

    @Override
    public PaginatedTransactions findAllByCategory(String username, String category, Integer page) {
        transactionUtils.verifyAllCategory(category);

        var user = transactionUtils.findUserEntity(username);
        var categoryEntity = mapper.map(category, CategoryEntity.class);

        var list = transactionRepository.findAllByCategoryAndUser(
                categoryEntity,
                user,
                PageRequest.of(page, 10, Sort.by("date", "createdIn").descending())
        );

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedTransactions = new PaginatedTransactions();
        paginatedTransactions.setList(
                list.stream()
                        .map(transactionEntity -> mapper.map(transactionEntity, Transaction.class))
                        .collect(Collectors.toList())
        );
        paginatedTransactions.setTotal(transactionRepository.countByCategoryAndUser(categoryEntity, user));
        return paginatedTransactions;
    }

    @Override
    public PaginatedTransactions findAllByType(String username, String type, Integer page) {
        transactionUtils.verifyAllTransactionTypes(type);

        var user = transactionUtils.findUserEntity(username);
        var transactionTypeEntity = mapper.map(type, TransactionTypeEntity.class);

        var list = transactionRepository.findAllByTypeAndUser(
                transactionTypeEntity,
                user,
                PageRequest.of(page, 10, Sort.by("date", "createdIn").descending())
        );

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedTransactions = new PaginatedTransactions();
        paginatedTransactions.setList(
                list.stream()
                        .map(transactionEntity -> mapper.map(transactionEntity, Transaction.class))
                        .collect(Collectors.toList())
        );
        paginatedTransactions.setTotal(transactionRepository.countByTypeAndUser(transactionTypeEntity, user));
        return paginatedTransactions;
    }

    @Override
    public PaginatedTransactions findAllByDate(String username, LocalDate date, Integer page) {
        var user = transactionUtils.findUserEntity(username);

        var list = transactionRepository.findAllByDateAndUser(
                date,
                transactionUtils.findUserEntity(username),
                PageRequest.of(page, 10, Sort.by("date", "createdIn").descending())
        );

        if (list.isEmpty())
            throw new InvalidPage(page);

        var paginatedTransactions = new PaginatedTransactions();
        paginatedTransactions.setList(
                list.stream()
                        .map(transactionEntity -> mapper.map(transactionEntity, Transaction.class))
                        .collect(Collectors.toList())
        );
        paginatedTransactions.setTotal(transactionRepository.countByDateAndUser(date, user));
        return paginatedTransactions;
    }
}
