package com.my.Closet.Service;

import com.my.Closet.DTO.UserDTO;
import com.my.Closet.model.Closet;
import com.my.Closet.model.User;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.exception.UserServiceException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.UserRepository;
import com.my.Closet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private ClosetRepository closetRepository;

    @Test
    void createUser_Success() {
        // Mocking userDTO
        UserDTO userDTO = UserDTO.builder()
                .username("testUser")
                .email("test@example.com")
                .mobilePhone("123456789")
                .build();

        // Mocking the behavior of userRepository.save
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(UUID.randomUUID()); // Simulating ID generation by repository
            return savedUser;
        });

        // Calling the service method
        User createdUser = userService.createUser(userDTO);

        // Verifying that userRepository.save was called with the correct user object
        verify(userRepository).save(argThat(user ->
                user.getUsername().equals(userDTO.getUsername()) &&
                        user.getEmail().equals(userDTO.getEmail()) &&
                        user.getMobilePhone().equals(userDTO.getMobilePhone())
        ));

        // Asserting that the created user is not null
        assertNotNull(createdUser);
        // Asserting that the created user has a non-null ID
        assertNotNull(createdUser.getId());
        // Asserting that the created user has the correct username, email, and mobile phone
        assertEquals(userDTO.getUsername(), createdUser.getUsername());
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        assertEquals(userDTO.getMobilePhone(), createdUser.getMobilePhone());
    }

    @Test
    void createUser_Failure() {
        // Mocking userDTO
        UserDTO userDTO = UserDTO.builder()
                .username("testUser")
                .email("test@example.com")
                .mobilePhone("123456789")
                .build();

        // Mocking userRepository.save to throw an exception
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        // Calling the service method and expecting UserServiceException
        assertThrows(UserServiceException.class, () -> userService.createUser(userDTO));

        // Verifying that userRepository.save was called
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findAllUsersNotDeleted_Success() {
        // Mocking a list of users
        List<User> users = List.of(
                User.builder().id(UUID.randomUUID()).username("user1").email("user1@example.com").mobilePhone("123").deleted(false).build(),
                User.builder().id(UUID.randomUUID()).username("user2").email("user2@example.com").mobilePhone("456").deleted(false).build()
        );

        // Mocking the behavior of userRepository.findByDeletedFalse
        when(userRepository.findByDeletedFalse()).thenReturn(users);

        // Calling the service method
        List<User> result = userService.findAllUsersNotDeleted();

        // Asserting that the result matches the mocked users
        assertEquals(users.size(), result.size());
        assertTrue(result.containsAll(users));

        // Verifying that userRepository.findByDeletedFalse was called
        verify(userRepository).findByDeletedFalse();
    }

    @Test
    void findAllUsersNotDeleted_Exception() {
        // Mocking userRepository.findByDeletedFalse to throw an exception
        when(userRepository.findByDeletedFalse()).thenThrow(RuntimeException.class);

        // Calling the service method and expecting UserServiceException
        assertThrows(UserServiceException.class, () -> userService.findAllUsersNotDeleted());

        // Verifying that userRepository.findByDeletedFalse was called
        verify(userRepository).findByDeletedFalse();
    }

    @Test
    void findAllUsersNotDeleted_EmptyList() {
        // Mocking an empty list of users
        List<User> emptyList = Collections.emptyList();

        // Mocking the behavior of userRepository.findByDeletedFalse to return an empty list
        when(userRepository.findByDeletedFalse()).thenReturn(emptyList);

        // Calling the service method
        List<User> result = userService.findAllUsersNotDeleted();

        // Asserting that the result is an empty list
        assertTrue(result.isEmpty());

        // Verifying that userRepository.findByDeletedFalse was called
        verify(userRepository).findByDeletedFalse();
    }

    @Test
    void getUserById_UserFound() {
        // Mocking user ID
        UUID userId = UUID.randomUUID();

        // Mocking the found user
        User user = User.builder()
                .id(userId)
                .username("testUser")
                .email("test@example.com")
                .mobilePhone("123456789")
                .deleted(false)
                .build();
        Optional<User> optionalUser = Optional.of(user);

        // Mocking the behavior of userRepository.findByIdAndDeletedFalse
        when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(optionalUser);

        // Calling the service method
        UserDTO result = userService.getUserById(userId);

        // Asserting that the result matches the mocked user
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getMobilePhone(), result.getMobilePhone());

        // Verifying that userRepository.findByIdAndDeletedFalse was called
        verify(userRepository).findByIdAndDeletedFalse(userId);
    }

    @Test
    void getUserById_UserNotFound() {
        // Mocking user ID
        UUID userId = UUID.randomUUID();

        // Mocking optional user as empty
        when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(Optional.empty());

        // Calling the service method and expecting UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        // Verifying that userRepository.findByIdAndDeletedFalse was called
        verify(userRepository).findByIdAndDeletedFalse(userId);
    }

    @Test
    void deleteUser_UserFound() {
        // Mocking user ID
        UUID userId = UUID.randomUUID();

        // Mocking the found user
        User user = User.builder()
                .id(userId)
                .username("testUser")
                .email("test@example.com")
                .mobilePhone("123456789")
                .deleted(false)
                .build();
        Optional<User> optionalUser = Optional.of(user);

        // Mocking the behavior of userRepository.findByIdAndDeletedFalse
        when(userRepository.findByIdAndDeletedFalse(userId)).thenReturn(optionalUser);

        // Calling the service method
        userService.deleteUser(userId);

        // Verifying that userRepository.findByIdAndDeletedFalse was called
        verify(userRepository).findByIdAndDeletedFalse(userId);

        // Verifying that userRepository.save was called with the correct user object
        verify(userRepository).save(argThat(savedUser ->
                savedUser.getId().equals(user.getId()) &&
                        savedUser.getUsername().equals(user.getUsername()) &&
                        savedUser.getEmail().equals(user.getEmail()) &&
                        savedUser.getMobilePhone().equals(user.getMobilePhone()) &&
                        savedUser.getDeleted().equals(user.getDeleted())
        ));
    }

    @Test
    void addClosetToUser_Success() {
        // Mocking user ID and closet ID
        UUID userId = UUID.randomUUID();
        UUID closetId = UUID.randomUUID();

        // Mocking the found user and closet
        User user = User.builder()
                .id(userId)
                .username("testUser")
                .email("test@example.com")
                .mobilePhone("123456789")
                .build();
        Optional<User> optionalUser = Optional.of(user);

        Closet closet = Closet.builder()
                .id(closetId)
                .name("Test Closet")
                .user(null)
                .build();
        Optional<Closet> optionalCloset = Optional.of(closet);

        // Mocking the behavior of userRepository.findById and closetRepository.findById
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        when(closetRepository.findById(closetId)).thenReturn(optionalCloset);

        // Calling the service method
        userService.addClosetToUser(userId, closetId);

        // Verifying that userRepository.findById and closetRepository.findById were called
        verify(userRepository).findById(userId);
        verify(closetRepository).findById(closetId);

        // Verifying that closet.setUser and closetRepository.save were called
        assertNotNull(closet.getUser());
        assertEquals(user, closet.getUser());
        verify(closetRepository).save(closet);
    }
}
