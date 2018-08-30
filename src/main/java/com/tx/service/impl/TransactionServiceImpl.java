package com.tx.service.impl;

import com.tx.model.Transaction;
import com.tx.repository.StatisticRepository;
import com.tx.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private StatisticRepository statisticRepository;

    public TransactionServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.statisticRepository.computeTransaction(transaction);
    }

    @Override
    public void deleteAllTransactions() {
        this.statisticRepository.deleteAllStatistics();
    }

}
