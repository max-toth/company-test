package com.company.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OverallStat {

    private long personsCount;
    private long attendersCount;
    private long attendersCompletedCount;
    private long attendersFullPassedCount;

}
