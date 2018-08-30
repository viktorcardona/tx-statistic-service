package com.tx.model;

import com.tx.validator.AgeLimit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private BigDecimal amount;

    @AgeLimit( timeUnit = ChronoUnit.SECONDS, amount = 60 )
    @NotNull
    @Past
    private LocalDateTime timestamp;


}
