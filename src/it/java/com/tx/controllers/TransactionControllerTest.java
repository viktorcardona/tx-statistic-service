package com.tx.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tx.exception.TxResponseEntityExceptionHandler;
import com.tx.model.Transaction;
import com.tx.repository.StatisticRepository;
import com.tx.service.TransactionService;
import com.tx.service.impl.TransactionServiceImpl;
import com.tx.util.TransactionUtilUnitTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {

    private TransactionController transactionController;

    @Mock
    private StatisticRepository statisticRepository;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        TransactionService transactionService = new TransactionServiceImpl(statisticRepository);
        transactionController = new TransactionController(transactionService);

        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new TxResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void addTransactionHttpStatus201() throws Exception {

        BigDecimal amount = BigDecimal.ONE;
        LocalDateTime timestamp = TransactionUtilUnitTest.now();

        Transaction tx = TransactionUtilUnitTest.buildTx(amount, timestamp);

        doNothing().when(statisticRepository).computeTransaction(tx);

        mockMvc.perform(post(TransactionController.URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTransaction(amount, timestamp)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addTransactionHttpStatus204() throws Exception {

        BigDecimal amount = BigDecimal.ONE;
        LocalDateTime timestamp = TransactionUtilUnitTest.nowOffset(-61000l);

        Transaction tx = TransactionUtilUnitTest.buildTx(amount, timestamp);

        doNothing().when(statisticRepository).computeTransaction(tx);

        mockMvc.perform(post(TransactionController.URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTransaction(amount, timestamp)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTransactions() throws Exception {
        mockMvc.perform(delete(TransactionController.URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(statisticRepository).deleteAllStatistics();
    }

    private Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneOffset.UTC).toInstant();
    }

    private String buildTransaction(BigDecimal amount, LocalDateTime timestamp) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode body = mapper.createObjectNode();
        ObjectNode bodyObject = (ObjectNode) body;
        Instant instant = toInstant(timestamp);
        bodyObject.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(instant));
        bodyObject.put("amount", amount.toPlainString());
        return bodyObject.toString();
    }

}