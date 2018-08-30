package com.tx.service;

import com.tx.model.Transaction;
import com.tx.repository.StatisticRepository;
import com.tx.service.impl.TransactionServiceImpl;
import com.tx.util.TransactionUtilUnitTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private StatisticRepository statisticRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(statisticRepository);
    }

    @Test
    public void addTransactionYounger() {
        //given
        BigDecimal amount = BigDecimal.ONE;
        LocalDateTime age = TransactionUtilUnitTest.now();
        Transaction tx = TransactionUtilUnitTest.buildTx(amount, age);

        doNothing().when(statisticRepository).computeTransaction(tx);

        //when
        transactionService.addTransaction(tx);

        //then
        verify(statisticRepository).computeTransaction(tx);
    }

    @Test
    public void deleteAllTransactions() {
        //when
        transactionService.deleteAllTransactions();

        //then
        verify(statisticRepository).deleteAllStatistics();
    }


}