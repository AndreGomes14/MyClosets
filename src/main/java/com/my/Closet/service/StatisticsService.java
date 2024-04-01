package com.my.Closet.service;

import java.util.Map;
import java.util.UUID;

public interface StatisticsService {
    String mostCommonClub(UUID userId);

    String mostCommonBrand(UUID userId);

    String mostCommonColor(UUID userId);

    String mostCommonDecade(UUID userId);

    double averageBuyPrice(UUID userId);

    int totalJerseysCount(UUID userId);
}
