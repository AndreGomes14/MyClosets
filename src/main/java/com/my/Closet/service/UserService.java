package com.my.Closet.service;

import com.my.Closet.DTO.UserDTO;
import com.my.Closet.entity.Closet;
import com.my.Closet.entity.User;
import com.my.Closet.exception.ClosetNotFoundException;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClosetRepository closetRepository;

    @Autowired
    public UserService(UserRepository userRepository, ClosetRepository closetRepository) {
        this.userRepository = userRepository;
        this.closetRepository = closetRepository;
    }

    public User createUser(UserDTO userDTO) {
        // Use the UserDTO builder to create a new User entity
        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .mobilePhone(userDTO.getMobilePhone())
                .build();

        // Save the new user entity to the database
        return userRepository.save(user);
    }

    public List<User> findAllUsersNotDeleted() {
        // Retrieve all users that are not deleted from the UserRepository
        return userRepository.findByIsDeletedFalse();
    }

    public User getUserById(UUID userId) {
        // Retrieve the user by ID from the UserRepository
        Optional<User> userOptional = userRepository.findByIdAndIsDeletedFalse(userId);
        // Return the user if found, otherwise throw an exception
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public void addClosetToUser(UUID userId, UUID closetId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Closet> optionalCloset = closetRepository.findById(closetId);
            if (optionalCloset.isPresent()) {
                Closet closet = optionalCloset.get();
                closet.setUser(user);
                closetRepository.save(closet);
            } else {
                throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
            }
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    public Optional<UserDTO> getUserWithClosets(UUID userId) {
        Optional<User> userOptional = userRepository.getUserWithClosets(userId);
        return userOptional.map(user -> UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobilePhone(user.getMobilePhone())
                .isDeleted(user.getIsDeleted())
                .build());
    }
}
