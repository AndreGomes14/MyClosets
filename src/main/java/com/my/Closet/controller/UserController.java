package com.my.Closet.controller;

import com.my.Closet.DTO.UserDTO;
import com.my.Closet.entity.User;
import com.my.Closet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        // Create the user using the UserService
        User createdUser = userService.createUser(userDTO);
        // Return the created user along with a HTTP status code
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> findAllUsersNotDeleted() {
        // Retrieve all users that are not deleted using the UserService
        List<User> users = userService.findAllUsersNotDeleted();
        // Return the list of users along with a HTTP status code
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUserByID")
    public ResponseEntity<User> getUserById(@RequestBody UUID userId) {
        // Retrieve the user by ID using the UserService
        User user = userService.getUserById(userId);
        // Return the user along with a HTTP status code
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestBody UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addClosetToUser")
    public ResponseEntity<Void> addClosetToUser(@RequestBody UUID userId, @RequestBody UUID closetId) {
        userService.addClosetToUser(userId, closetId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getClosetByUserID")
    public ResponseEntity<UserDTO> getUserWithClosets(@RequestBody UUID userId) {
        Optional<UserDTO> userWithClosets = userService.getUserWithClosets(userId);
        return userWithClosets.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
