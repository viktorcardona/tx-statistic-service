package com.tx.util;

import com.tx.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TransactionUtilUnitTest {

    public static Transaction buildTx(BigDecimal amount, LocalDateTime timestamp) {
        return Transaction.builder().amount(amount).timestamp(timestamp).build();
    }

    public static LocalDateTime now() {
        Instant now = Instant.now();
        return LocalDateTime.ofInstant(now, ZoneOffset.UTC);
    }

    public static LocalDateTime nowOffset(long offset) {
        Instant timestamp = Instant.now().plusMillis(offset);
        return LocalDateTime.ofInstant(timestamp, ZoneOffset.UTC);
    }

}
