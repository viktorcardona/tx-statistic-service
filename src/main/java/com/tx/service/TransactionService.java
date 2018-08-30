package com.tx.service;

import com.tx.model.Transaction;

public interface TransactionService {

    void addTransaction(Transaction transaction);
    void deleteAllTransactions();
}
