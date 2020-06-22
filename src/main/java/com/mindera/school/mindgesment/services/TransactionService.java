package com.mindera.school.mindgesment.services;

import com.mindera.school.mindgesment.http.models.Transaction;

public interface TransactionService {

    Transaction add(Transaction newTransaction, String username);

    Transaction edit(String transactionId, Transaction editedTransaction, String username);

    void delete(String transactionId, String username);

    void delete(String transaction);
}
