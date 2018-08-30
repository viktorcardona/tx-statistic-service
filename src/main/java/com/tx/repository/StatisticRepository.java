package com.tx.repository;

import com.tx.model.Statistic;
import com.tx.model.Transaction;

public interface StatisticRepository {

    void computeTransaction(Transaction transaction);
    void deleteAllStatistics();
    Statistic getStatistic();
}
