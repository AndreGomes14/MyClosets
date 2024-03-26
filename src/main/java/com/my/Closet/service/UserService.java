package com.my.Closet.service;

import com.my.Closet.DTO.UserDTO;
import com.my.Closet.entity.Closet;
import com.my.Closet.entity.User;
import com.my.Closet.exception.ClosetNotFoundException;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.exception.UserServiceException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ClosetRepository closetRepository;


    @Autowired
    public UserService(UserRepository userRepository, ClosetRepository closetRepository) {
        this.userRepository = userRepository;
        this.closetRepository = closetRepository;
    }

    public User createUser(UserDTO userDTO) {
        try {
            // Use the UserDTO builder to create a new User entity
            User user = User.builder()
                    .username(userDTO.getUsername())
                    .email(userDTO.getEmail())
                    .mobilePhone(userDTO.getMobilePhone())
                    .deleted(false)
                    .build();

            // Save the new user entity to the database
            User createdUser = userRepository.save(user);

            // Log success message
            log.info("User created successfully: {}", createdUser);

            // Return the created user
            return createdUser;
        } catch (Exception ex) {
            // Log error message
            log.error("Error creating user: {}", ex.getMessage());
            // Throw a custom service exception
            throw new UserServiceException("Failed to create user", ex);
        }
    }

    public List<User> findAllUsersNotDeleted() {
        log.info("Fetching all users that are not deleted");

        try {
            List<User> users = userRepository.findByDeletedFalse();
            log.info("Users found: {}", users);
            return users;
        } catch (Exception e) {
            log.error("Error occurred while fetching all users not deleted", e);
            throw new UserServiceException("Error occurred while fetching all users not deleted", e);
        }
    }

    public UserDTO getUserById(UUID userId) {
        log.info("Fetching user with ID: {}", userId);

        // Retrieve the user by ID from the UserRepository
        Optional<User> userOptional = userRepository.findByIdAndDeletedFalse(userId);

        // Map the User entity to a UserDTO or throw exception if not found
        return userOptional.map(user -> {
            log.info("User found: {}", user);
            return UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .mobilePhone(user.getMobilePhone())
                    .build();
        }).orElseThrow(() -> {
            log.warn("User not found with ID: {}", userId);
            return new UserNotFoundException("User not found with ID: " + userId);
        });
    }

    public void deleteUser(UUID id) {
        try {
            User user = userRepository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

            user.setDeleted(true);
            userRepository.save(user);

            // Log success message
            log.info("User deleted successfully: {}", user);
        } catch (Exception ex) {
            // Log error message
            log.error("Error deleting user with ID {}: {}", id, ex.getMessage());
            // Throw a custom service exception
            throw new UserServiceException("Failed to delete user", ex);
        }
    }

    public void addClosetToUser(UUID userId, UUID closetId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Optional<Closet> optionalCloset = closetRepository.findById(closetId);
                if (optionalCloset.isPresent()) {
                    Closet closet = optionalCloset.get();
                    closet.setUser(user);
                    closetRepository.save(closet);
                    // Log success message
                    log.info("Closet added to user successfully. User ID: {}, Closet ID: {}", userId, closetId);
                } else {
                    throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
                }
            } else {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }
        } catch (Exception ex) {
            // Log error message
            log.error("Error adding closet to user. User ID: {}, Closet ID: {}", userId, closetId, ex);
            throw new UserServiceException("Error adding closet to user", ex);
        }
    }
}
