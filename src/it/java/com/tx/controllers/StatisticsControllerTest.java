package com.tx.controllers;

import com.tx.exception.TxResponseEntityExceptionHandler;
import com.tx.model.Statistic;
import com.tx.repository.StatisticRepository;
import com.tx.service.StatisticsService;
import com.tx.service.impl.StatisticsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static com.tx.repository.impl.StatisticRepositoryImpl.DEFAULT_STATISTIC;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsControllerTest {

    private StatisticsController statisticsController;

    @Mock
    private StatisticRepository statisticRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StatisticsService statisticsService = new StatisticsServiceImpl(statisticRepository);
        statisticsController = new StatisticsController(statisticsService);

        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController)
                .setControllerAdvice(new TxResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void retrieveStatisticsZero() throws Exception {

        when(statisticRepository.getStatistic()).thenReturn(DEFAULT_STATISTIC);

        mockMvc.perform(get(StatisticsController.URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(StatisticsServiceImpl.ZERO)))
                .andExpect(jsonPath("$.avg", is(StatisticsServiceImpl.ZERO)))
                .andExpect(jsonPath("$.max", is(StatisticsServiceImpl.ZERO)))
                .andExpect(jsonPath("$.min", is(StatisticsServiceImpl.ZERO)))
                .andExpect(jsonPath("$.count", is(0)));
    }

    @Test
    public void retrieveStatistics() throws Exception {

        when(statisticRepository.getStatistic()).thenReturn(Statistic.builder().count(3)
                .sum(new BigDecimal("12"))
                .avg(new BigDecimal("4"))
                .max(new BigDecimal("10"))
                .min(new BigDecimal("1"))
                .build());

        mockMvc.perform(get(StatisticsController.URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is("12.00")))
                .andExpect(jsonPath("$.avg", is("4.00")))
                .andExpect(jsonPath("$.max", is("10.00")))
                .andExpect(jsonPath("$.min", is("1.00")))
                .andExpect(jsonPath("$.count", is(3)));
    }

}