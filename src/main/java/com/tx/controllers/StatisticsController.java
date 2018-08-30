package com.tx.controllers;

import com.tx.model.StatisticResponse;
import com.tx.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StatisticsController {

    public static final String URL = "/statistics";

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(URL)
    public StatisticResponse retrieveStatistics() {
        return this.statisticsService.getStatistic();
    }

}
