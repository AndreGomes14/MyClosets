package com.my.Closet.service;

import com.my.Closet.model.Closet;
import com.my.Closet.model.Jersey;
import com.my.Closet.model.User;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClosetRepository closetRepository;

    @Override
    public String mostCommonClub(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

        try {
            Map<String, Integer> clubCounts = new HashMap<>();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each club
                        clubCounts.put(jersey.getClubName(), clubCounts.getOrDefault(jersey.getClubName(), 0) + 1);
                    }
                }

                // Find the club with the highest count
                String mostCommonClub = null;
                int maxCount = 0;
                for (Map.Entry<String, Integer> entry : clubCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        mostCommonClub = entry.getKey();
                        maxCount = entry.getValue();
                    }
                }
                return mostCommonClub;
            } else {
                logger.warn("User with ID {} not found.", userId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating most common club for user with ID {}: {}", userId, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String mostCommonBrand(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

        try {
            Map<String, Integer> brandCounts = new HashMap<>();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each brand
                        brandCounts.put(jersey.getBrand(), brandCounts.getOrDefault(jersey.getBrand(), 0) + 1);
                    }
                }

                // Find the brand with the highest count
                String mostCommonBrand = null;
                int maxCount = 0;
                for (Map.Entry<String, Integer> entry : brandCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        mostCommonBrand = entry.getKey();
                        maxCount = entry.getValue();
                    }
                }
                return mostCommonBrand;
            } else {
                logger.warn("User with ID {} not found.", userId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating most common brand for user with ID {}: {}", userId, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String mostCommonColor(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class); // Replace YourClassName with the name of your class

        try {
            Map<String, Integer> colorCounts = new HashMap<>();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each color
                        colorCounts.put(jersey.getColor(), colorCounts.getOrDefault(jersey.getColor(), 0) + 1);
                    }
                }

                // Find the color with the highest count
                String mostCommonColor = null;
                int maxCount = 0;
                for (Map.Entry<String, Integer> entry : colorCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        mostCommonColor = entry.getKey();
                        maxCount = entry.getValue();
                    }
                }
                return mostCommonColor;
            } else {
                logger.warn("User with ID {} not found.", userId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating most common color for user with ID {}: {}", userId, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String mostCommonDecade(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

        try {
            Map<String, Integer> decadeCounts = new HashMap<>();
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Extract the decade from jersey season
                        String season = jersey.getSeason();
                        String decade = getDecade(season);

                        // Increment count for each decade
                        decadeCounts.put(decade, decadeCounts.getOrDefault(decade, 0) + 1);
                    }
                }

                // Find the decade with the highest count
                String mostCommonDecade = null;
                int maxCount = 0;
                for (Map.Entry<String, Integer> entry : decadeCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        mostCommonDecade = entry.getKey();
                        maxCount = entry.getValue();
                    }
                }
                return mostCommonDecade;
            } else {
                logger.warn("User with ID {} not found.", userId);
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating most common decade for user with ID {}: {}", userId, e.getMessage(), e);
        }
        return null;
    }

    // Utility method to extract decade from season
    private String getDecade(String season) {
        // Assuming season format is "2000/2001"
        String[] parts = season.split("/");
        int year = Integer.parseInt(parts[0]);
        int decadeStartYear = year - (year % 10);
        return decadeStartYear + "/" + (decadeStartYear + 9);
    }

    public double averageBuyPrice(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

        try {
            double totalBuyPrice = 0.0;
            int jerseyCount = 0;

            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Sum up the buy prices of all jerseys
                        totalBuyPrice += Double.parseDouble(jersey.getBuyPrice());
                        jerseyCount++;
                    }
                }

                // Calculate the average buy price
                if (jerseyCount > 0) {
                    return totalBuyPrice / jerseyCount;
                } else {
                    return 0.0; // Handle the case when there are no jerseys
                }
            } else {
                logger.warn("User with ID {} not found.", userId);
                return 0.0; // Handle the case when user is not found
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating average buy price for user with ID {}: {}", userId, e.getMessage(), e);
            return 0.0; // Return 0.0 in case of any exception
        }
    }

    public int totalJerseysCount(UUID userId) {
        Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

        try {
            int totalJerseys = 0;
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Iterate through the closets of the specific user
                for (Closet closet : user.getClosets()) {
                    // Sum up the counts of jerseys in each closet
                    totalJerseys += closet.getJerseys().size();
                }
                return totalJerseys;
            } else {
                logger.warn("User with ID {} not found.", userId);
                return 0; // Handle the case when user is not found
            }
        } catch (Exception e) {
            logger.error("Error occurred while calculating total jerseys count for user with ID {}: {}", userId, e.getMessage(), e);
            return 0; // Return 0 in case of any exception
        }
    }
}
