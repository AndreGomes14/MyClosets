package com.my.Closet.Service;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.DTO.UserDTO;
import com.my.Closet.model.Closet;
import com.my.Closet.model.Jersey;
import com.my.Closet.model.User;
import com.my.Closet.exception.ClosetNotFoundException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.JerseyRepository;
import com.my.Closet.service.ClosetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClosetServiceTest {
    @Mock
    private ClosetRepository closetRepository;

    @InjectMocks
    private ClosetService closetService;

    @Mock
    private JerseyRepository jerseyRepository;
    @Test
    void createCloset_Success() {
        // Mocking the input DTO
        ClosetDTO createClosetDTO = new ClosetDTO();
        createClosetDTO.setUser(new UserDTO(UUID.randomUUID(), "testuser", "test@example.com", "123456789"));

        // Mocking the saved closet
        Closet savedCloset = Closet.builder()
                .id(UUID.randomUUID())
                .user(User.builder()
                        .id(createClosetDTO.getUser().getId())
                        .username(createClosetDTO.getUser().getUsername())
                        .email(createClosetDTO.getUser().getEmail())
                        .mobilePhone(createClosetDTO.getUser().getMobilePhone())
                        .deleted(false)
                        .build())
                .deleted(false)
                .build();

        // Mocking the behavior of closetRepository.save
        when(closetRepository.save(any(Closet.class))).thenReturn(savedCloset);

        // Calling the service method
        ClosetDTO result = closetService.createCloset(createClosetDTO);

        // Asserting the result
        assertEquals(createClosetDTO.getName(), result.getName());
        assertEquals(createClosetDTO.getUser().getId(), result.getUser().getId());
        assertEquals(createClosetDTO.getUser().getUsername(), result.getUser().getUsername());
        assertEquals(createClosetDTO.getUser().getEmail(), result.getUser().getEmail());
        assertEquals(createClosetDTO.getUser().getMobilePhone(), result.getUser().getMobilePhone());
        assertEquals(false, result.getDeleted());
    }


    @Test
    void getClosetsByUserId_Success() {
        // Mocking user ID
        UUID userId = UUID.randomUUID();

        // Mocking the list of closets from repository
        List<Closet> closets = new ArrayList<>();
        closets.add(createMockCloset(UUID.randomUUID()));
        closets.add(createMockCloset(UUID.randomUUID()));

        // Mocking the behavior of closetRepository.findByUserIdAndDeletedIsFalse
        when(closetRepository.findByUserIdAndDeletedIsFalse(userId)).thenReturn(closets);

        // Calling the service method
        List<ClosetDTO> result = closetService.getClosetsByUserId(userId);

        // Asserting the result size
        assertEquals(closets.size(), result.size());

        // Asserting the content of each DTO
        for (int i = 0; i < closets.size(); i++) {
            assertEquals(closets.get(i).getId(), result.get(i).getId());
            assertEquals(closets.get(i).getName(), result.get(i).getName());
            // Add more assertions if needed for other fields
        }
    }

    @Test
    void deleteCloset_Success() {
        // Mocking closet ID
        UUID closetId = UUID.randomUUID();

        // Mocking optional closet
        Closet closet = new Closet();
        closet.setId(closetId);
        Optional<Closet> optionalCloset = Optional.of(closet);

        // Mocking the behavior of closetRepository.findById
        when(closetRepository.findById(closetId)).thenReturn(optionalCloset);

        // Calling the service method
        closetService.deleteCloset(closetId);

        // Verifying that closet.setDeleted was called with true
        assertTrue(closet.getDeleted());

        // Verifying that closetRepository.save was called
        verify(closetRepository).save(closet);

        // Verifying that the closet was soft deleted
        verify(closetRepository).save(closet);
    }

    @Test
    void deleteCloset_ClosetNotFound() {
        // Mocking closet ID
        UUID closetId = UUID.randomUUID();

        // Mocking optional closet as empty
        Optional<Closet> optionalCloset = Optional.empty();

        // Mocking the behavior of closetRepository.findById
        when(closetRepository.findById(closetId)).thenReturn(optionalCloset);

        // Calling the service method and expecting ClosetNotFoundException
        assertThrows(ClosetNotFoundException.class, () -> closetService.deleteCloset(closetId));

        // Verifying that closet.setDeleted and closetRepository.save were not called
        verify(closetRepository, never()).save(any());
    }

    @Test
    void getAllJerseysByClosetId_Success() {
        // Mocking closet ID
        UUID closetId = UUID.randomUUID();

        // Mocking a list of jerseys associated with the closet
        List<Jersey> jerseys = new ArrayList<>();
        jerseys.add(new Jersey(UUID.randomUUID(), "Jersey 1", "Club 1"));
        jerseys.add(new Jersey(UUID.randomUUID(), "Jersey 2", "Club 2"));

        // Mocking the optional closet
        Closet closet = new Closet();
        closet.setId(closetId);
        closet.setJerseys(jerseys);
        Optional<Closet> optionalCloset = Optional.of(closet);

        // Mocking the behavior of closetRepository.findById
        when(closetRepository.findById(closetId)).thenReturn(optionalCloset);

        // Calling the service method
        List<JerseyDTO> result = closetService.getAllJerseysByClosetId(closetId);

        // Asserting the result size
        assertEquals(jerseys.size(), result.size());

        // Asserting the content of each DTO
        for (int i = 0; i < jerseys.size(); i++) {
            assertEquals(jerseys.get(i).getId(), result.get(i).getId());
            // Add more assertions if needed for other fields
        }
    }

    @Test
    void getAllJerseysByClosetId_ClosetNotFound() {
        // Mocking closet ID
        UUID closetId = UUID.randomUUID();

        // Mocking optional closet as empty
        Optional<Closet> optionalCloset = Optional.empty();

        // Mocking the behavior of closetRepository.findById
        when(closetRepository.findById(closetId)).thenReturn(optionalCloset);

        // Calling the service method and expecting ClosetNotFoundException
        assertThrows(ClosetNotFoundException.class, () -> closetService.getAllJerseysByClosetId(closetId));
    }

    private Closet createMockCloset(UUID id) {
        Closet closet = new Closet();
        closet.setId(id);
        // Add more fields initialization if needed
        return closet;
    }
}
