package com.my.Closet.service;

import java.util.Map;

public interface StatisticsService {
    Map<String, Map<String, Integer>> calculateStatistics();

    String mostCommonClub();

    String mostCommonBrand();

    String mostCommonColor();

    String mostCommonDecade();

    double averageBuyPrice();

    int totalJerseysCount();
}
