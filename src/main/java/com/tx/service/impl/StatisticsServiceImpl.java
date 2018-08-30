package com.tx.service.impl;

import com.tx.model.Statistic;
import com.tx.model.StatisticResponse;
import com.tx.repository.StatisticRepository;
import com.tx.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    public static final String ZERO = "0.00";
    private static final StatisticResponse STATISTIC_RESPONSE_ZERO = StatisticResponse.builder().sum(ZERO).avg(ZERO).max(ZERO).min(ZERO).build();

    private StatisticRepository statisticRepository;

    public StatisticsServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public StatisticResponse getStatistic() {
        return getStatisticResponse();
    }

    private StatisticResponse getStatisticResponse() {
        Statistic statistic = this.statisticRepository.getStatistic();
        if (statistic.getCount() == 0) {
            return STATISTIC_RESPONSE_ZERO;
        }
        return StatisticResponse.builder()
                .sum(roundBigDecimal(statistic.getSum()))
                .avg(roundBigDecimal(statistic.getAvg()))
                .max(roundBigDecimal(statistic.getMax()))
                .min(roundBigDecimal(statistic.getMin()))
                .count(statistic.getCount())
                .build();
    }

    private String roundBigDecimal(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.toPlainString();
    }

}
