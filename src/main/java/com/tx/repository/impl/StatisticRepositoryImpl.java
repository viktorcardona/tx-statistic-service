package com.tx.repository.impl;

import com.tx.model.Statistic;
import com.tx.model.Transaction;
import com.tx.repository.StatisticRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StatisticRepositoryImpl implements StatisticRepository {

    public static final Statistic DEFAULT_STATISTIC = Statistic.builder()
            .count(0l)
            .sum(BigDecimal.ZERO)
            .avg(BigDecimal.ZERO)
            .min(new BigDecimal("999999999999"))
            .max(BigDecimal.ZERO)
            .build();

    private final Map<Long, Statistic> txStatistics;

    public StatisticRepositoryImpl() {
        this.txStatistics = new ConcurrentHashMap<>();
    }

    @Override
    public void computeTransaction(Transaction transaction) {
        this.txStatistics.compute(bucket().apply(transaction.getTimestamp()), mergeStatistics(tx2Statistic().apply(transaction)));
    }

    @Override
    public void deleteAllStatistics() {
        this.txStatistics.clear();
    }

    @Override
    public Statistic getStatistic() {
        return getLast60SecondsBuckets()
                .mapToObj(key -> key2Statistic().apply(key))
                .reduce(DEFAULT_STATISTIC, (s1, s2) -> s1.merge(s2));
    }

    private LongFunction<Statistic> key2Statistic() {
        return (key) -> this.txStatistics.getOrDefault(key, DEFAULT_STATISTIC);
    }

    private LongStream getLast60SecondsBuckets() {
        LocalDateTime now = LocalDateTime.now();
        Function<LocalDateTime, Long> bucket = bucket();
        return IntStream.rangeClosed(0, 59)
                .mapToLong(i -> bucket.apply(now.plusSeconds(-i)));
    }

    private Function<LocalDateTime, Long> bucket() {
        return (time) -> timeSetZeroNano2Long(time);
    }

    private Long timeSetZeroNano2Long(LocalDateTime time) {
        return time.withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    private BiFunction<Long, Statistic, Statistic> mergeStatistics(Statistic statistic) {
        return (k, v) -> v == null ? statistic : v.merge(statistic);
    }

    private Function<Transaction, Statistic> tx2Statistic() {
        return (tx) -> Statistic.builder().count(1)
                .min(tx.getAmount())
                .max(tx.getAmount())
                .avg(tx.getAmount())
                .sum(tx.getAmount())
                .build();
    }

}
