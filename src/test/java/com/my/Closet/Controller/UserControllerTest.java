package com.my.Closet.Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.UserDTO;
import com.my.Closet.controller.UserController;
import com.my.Closet.model.User;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.exception.UserServiceException;
import com.my.Closet.service.StatisticsService;
import com.my.Closet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private Model model;
    @Test
    void testCreateUser_Success() {
        // Given
        UserDTO userDTO = new UserDTO(); // You should populate this DTO with sample data
        User createdUser = new User(); // You should populate this object with sample data
        when(userService.createUser(userDTO)).thenReturn(createdUser);

        // When
        ResponseEntity<User> responseEntity = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdUser, responseEntity.getBody());
    }

    @Test
    void testCreateUser_UserServiceException() {
        // Given
        UserDTO userDTO = new UserDTO(); // You should populate this DTO with sample data
        when(userService.createUser(userDTO)).thenThrow(new UserServiceException("Test exception"));

        // When
        ResponseEntity<User> responseEntity = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testFindAllUsersNotDeleted_Success() {
        // Given
        List<User> expectedUsers = new ArrayList<>(); // You should populate this list with sample data
        when(userService.findAllUsersNotDeleted()).thenReturn(expectedUsers);

        // When
        ResponseEntity<List<User>> responseEntity = userController.findAllUsersNotDeleted();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUsers, responseEntity.getBody());
    }

    @Test
    void testFindAllUsersNotDeleted_Exception() {
        // Given
        when(userService.findAllUsersNotDeleted()).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<List<User>> responseEntity = userController.findAllUsersNotDeleted();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testGetUserById_Success() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with a sample ID
        UserDTO expectedUserDTO = new UserDTO(); // You should populate this DTO with sample data
        when(userService.getUserById(userId.getId())).thenReturn(expectedUserDTO);

        // When
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedUserDTO, responseEntity.getBody());
    }

    @Test
    void testGetUserById_UserNotFoundException() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with a sample ID
        when(userService.getUserById(userId.getId())).thenThrow(new UserNotFoundException("Test exception"));

        // When
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetUserById_Exception() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with a sample ID
        when(userService.getUserById(userId.getId())).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<UserDTO> responseEntity = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteUser_Success() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with sample data

        // When
        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).deleteUser(userId.getId());
    }

    @Test
    void testDeleteUser_UserServiceException() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with sample data
        doThrow(new UserServiceException("Test exception")).when(userService).deleteUser(userId.getId());

        // When
        ResponseEntity<Void> responseEntity = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testAddClosetToUser_Success() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with sample data
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with sample data

        // When
        ResponseEntity<Void> responseEntity = userController.addClosetToUser(userId, closetId);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(userService, times(1)).addClosetToUser(userId.getId(), closetId.getId());
    }

    @Test
    void testAddClosetToUser_UserServiceException() {
        // Given
        UserDTO userId = new UserDTO(); // You should populate this DTO with sample data
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with sample data
        doThrow(new UserServiceException("Test exception")).when(userService).addClosetToUser(userId.getId(), closetId.getId());

        // When
        ResponseEntity<Void> responseEntity = userController.addClosetToUser(userId, closetId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
