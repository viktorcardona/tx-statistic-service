package com.tx.service;

import com.tx.model.Statistic;
import com.tx.model.StatisticResponse;
import com.tx.repository.StatisticRepository;
import com.tx.service.impl.StatisticsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static com.tx.repository.impl.StatisticRepositoryImpl.DEFAULT_STATISTIC;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @Mock
    private StatisticRepository statisticRepository;

    private static final BigDecimal[] TX_EMPTY = new BigDecimal[]{};
    private static final BigDecimal[] TXS = new BigDecimal[]{BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE};

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        statisticsService = new StatisticsServiceImpl(statisticRepository);
    }

    @Test
    public void getStatisticZero() {

        //given
        when(statisticRepository.getStatistic()).thenReturn(DEFAULT_STATISTIC);

        //when
        StatisticResponse statisticResponse = statisticsService.getStatistic();

        //then
        assertThat(statisticResponse, notNullValue());
        assertThat(statisticResponse.getCount(), is(0l));
    }

    @Test
    public void getStatistic() {

        //given
        when(statisticRepository.getStatistic()).thenReturn(Statistic.builder().count(3)
                .sum(new BigDecimal("12"))
                .avg(new BigDecimal("4"))
                .max(new BigDecimal("10"))
                .min(new BigDecimal("1"))
                .build());

        //when
        StatisticResponse statisticResponse = statisticsService.getStatistic();

        //then
        assertThat(statisticResponse, notNullValue());
        assertThat(statisticResponse.getSum(), is("12.00"));
        assertThat(statisticResponse.getAvg(), is("4.00"));
        assertThat(statisticResponse.getMax(), is("10.00"));
        assertThat(statisticResponse.getMin(), is("1.00"));
        assertThat(statisticResponse.getCount(), is(3l));
    }

}