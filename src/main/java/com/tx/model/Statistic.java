package com.tx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {

    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;


    public Statistic merge(Statistic that) {
        final long newCount = this.count + that.count;
        final BigDecimal newSum = this.sum.add(that.sum);

        BigDecimal newAvg = null;

        if (newCount != 0) {
            newAvg = newSum.divide( new BigDecimal(newCount) , 2, RoundingMode.HALF_UP);
        } else {
            newAvg = BigDecimal.ZERO;
        }


        BigDecimal newMax = this.max.max(that.max);
        BigDecimal newMin = this.min.min(that.min);

        return Statistic.builder()
                .count(newCount)
                .sum(newSum)
                .avg(newAvg)
                .max(newMax)
                .min(newMin)
                .build();
    }


}
