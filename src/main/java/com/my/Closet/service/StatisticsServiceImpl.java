package com.my.Closet.service;

import com.my.Closet.model.Closet;
import com.my.Closet.model.Jersey;
import com.my.Closet.model.User;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClosetRepository closetRepository;

    @Override
    public Map<String, Map<String, Integer>> calculateStatistics() {
        Map<String, Map<String, Integer>> statistics = new HashMap<>();
        try {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                Map<String, Integer> userStatistics = new HashMap<>();
                for (Closet closet : user.getClosets()) {
                    int totalJerseys = closet.getJerseys().size();
                    userStatistics.put(closet.getName(), totalJerseys);
                }
                statistics.put(user.getUsername(), userStatistics);
            }
        } catch (Exception e) {
            // Log or handle the exception appropriately
        }
        return statistics;
    }

    @Override
    public String mostCommonClub() {
        try {
            Map<String, Integer> clubCounts = new HashMap<>();
            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each club
                        clubCounts.put(jersey.getClubName(), clubCounts.getOrDefault(jersey.getClubName(), 0) + 1);
                    }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String mostCommonBrand() {
        try {
            Map<String, Integer> brandCounts = new HashMap<>();
            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each brand
                        brandCounts.put(jersey.getBrand(), brandCounts.getOrDefault(jersey.getBrand(), 0) + 1);
                    }
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
        } catch (Exception e) {
            // Log or handle the exception appropriately
        }
        return null;
    }

    @Override
    public String mostCommonColor() {
        try {
            Map<String, Integer> colorCounts = new HashMap<>();
            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Increment count for each color
                        colorCounts.put(jersey.getColor(), colorCounts.getOrDefault(jersey.getColor(), 0) + 1);
                    }
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
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String mostCommonDecade() {
        try {
            Map<String, Integer> decadeCounts = new HashMap<>();
            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Extract the decade from jersey season
                        String season = jersey.getSeason();
                        String decade = getDecade(season);

                        // Increment count for each decade
                        decadeCounts.put(decade, decadeCounts.getOrDefault(decade, 0) + 1);
                    }
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
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
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

    @Override
    public double averageBuyPrice() {
        try {
            double totalBuyPrice = 0.0;
            int jerseyCount = 0;

            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    for (Jersey jersey : closet.getJerseys()) {
                        // Sum up the buy prices of all jerseys
                        totalBuyPrice += Double.parseDouble(jersey.getBuyPrice());
                        jerseyCount++;
                    }
                }
            }

            // Calculate the average buy price
            if (jerseyCount > 0) {
                return totalBuyPrice / jerseyCount;
            } else {
                return 0.0; // Handle the case when there are no jerseys
            }
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public int totalJerseysCount() {
        try {
            int totalJerseys = 0;
            List<User> users = userRepository.findAll();

            // Iterate through all users and their closets
            for (User user : users) {
                for (Closet closet : user.getClosets()) {
                    // Sum up the counts of jerseys in each closet
                    totalJerseys += closet.getJerseys().size();
                }
            }
            return totalJerseys;
        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }
        return 0;
    }
}
