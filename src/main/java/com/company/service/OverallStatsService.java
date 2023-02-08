package com.company.service;

import com.company.model.OverallStat;
import com.company.repository.OverallStatsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OverallStatsService {

    @Autowired
    private OverallStatsDao overallStatsDao;

    @Transactional
    public OverallStat stat() {
        return OverallStat.builder()
                .personsCount(overallStatsDao.personsCount())
                .attendersCount(overallStatsDao.attendersCount())
                .attendersCompletedCount(overallStatsDao.attendersCompletedCount())
                .attendersFullPassedCount(overallStatsDao.attendersFullPassedCount())
                .build()
                ;
    }

}
