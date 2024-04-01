package com.my.Closet.controller;

import org.springframework.ui.Model;
import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.UserDTO;
import com.my.Closet.model.User;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.exception.UserServiceException;
import com.my.Closet.service.StatisticsService;
import com.my.Closet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    private StatisticsService statisticsService;

    public UserController(UserService userService) {this.userService = userService;}


    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        try {
            // Create the user using the UserService
            User createdUser = userService.createUser(userDTO);
            // Log success message
            log.info("User created successfully: {}", createdUser);
            // Return the created user along with a HTTP status code
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserServiceException ex) {
            // Log error message
            log.error("Error creating user: {}", ex.getMessage());
            // Return an error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> findAllUsersNotDeleted() {
        log.info("Fetching all users that are not deleted");

        try {
            List<User> users = userService.findAllUsersNotDeleted();
            log.info("Users found: {}", users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all users not deleted", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getUserByID")
    public ResponseEntity<UserDTO> getUserById(@RequestBody UserDTO userId) {
        log.info("Fetching user with ID: {}", userId);

        try {
            UserDTO userDTO = userService.getUserById(userId.getId());
            log.info("User found: {}", userDTO);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.warn("User not found with ID: {}", userId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while fetching user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestBody UserDTO id) {
        try {
            userService.deleteUser(id.getId());
            // Log success message
            log.info("User deleted successfully with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserServiceException ex) {
            // Log error message
            log.error("Error deleting user with ID {}: {}", id, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addClosetToUser")
    public ResponseEntity<Void> addClosetToUser(@RequestBody UserDTO userId, @RequestBody ClosetDTO closetId) {
        try {
            userService.addClosetToUser(userId.getId(), closetId.getId());
            // Log success message
            log.info("Closet added to user successfully. User ID: {}, Closet ID: {}", userId, closetId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserServiceException ex) {
            // Log error message
            log.error("Error adding closet to user. User ID: {}, Closet ID: {}", userId, closetId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getUserStatisticsByID")
    public String getUserStatistics(@RequestBody UUID userId, Model model) {
        double averageBuyPrice = statisticsService.averageBuyPrice(userId);
        int totalJerseysCount = statisticsService.totalJerseysCount(userId);
        String mostCommonClub = statisticsService.mostCommonClub(userId);
        String mostCommonBrand = statisticsService.mostCommonBrand(userId);
        String mostCommonColor = statisticsService.mostCommonColor(userId);
        String mostCommonDecade = statisticsService.mostCommonDecade(userId);

        model.addAttribute("averageBuyPrice", averageBuyPrice);
        model.addAttribute("totalJerseysCount", totalJerseysCount);
        model.addAttribute("mostCommonClub", mostCommonClub);
        model.addAttribute("mostCommonBrand", mostCommonBrand);
        model.addAttribute("mostCommonColor", mostCommonColor);
        model.addAttribute("mostCommonDecade", mostCommonDecade);

        return "statistics";
    }
}
