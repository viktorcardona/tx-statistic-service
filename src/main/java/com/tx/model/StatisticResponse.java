package com.tx.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResponse {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;


}
