package com.my.Closet.Service;

import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Jersey;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.repository.JerseyRepository;
import com.my.Closet.service.JerseyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class JerseyServiceTest {

    @Mock
    private JerseyRepository jerseyRepository;

    @InjectMocks
    private JerseyService jerseyService;

    @Test
    void createJersey_Success() {
        // Mocking jersey DTO
        JerseyDTO jerseyDTO = new JerseyDTO();
        jerseyDTO.setClubName("Test Club");
        jerseyDTO.setPlayerName("Test Player");
        jerseyDTO.setNumber("10");
        jerseyDTO.setSeason("Test Season");
        jerseyDTO.setCompetition("Test Competition");
        jerseyDTO.setBrand("Test Brand");
        jerseyDTO.setColor("Test Color");
        jerseyDTO.setSize("Test Size");
        jerseyDTO.setCondition("Test Condition");
        jerseyDTO.setCategory("Test Category");
        jerseyDTO.setAcquisitionDate(String.valueOf(LocalDate.now()));
        jerseyDTO.setBuyPrice("100.0");

        // Mocking the created jersey
        Jersey createdJersey = new Jersey();
        createdJersey.setId(UUID.randomUUID());
        createdJersey.setClubName(jerseyDTO.getClubName());
        createdJersey.setPlayerName(jerseyDTO.getPlayerName());
        createdJersey.setNumber(jerseyDTO.getNumber());
        createdJersey.setSeason(jerseyDTO.getSeason());
        createdJersey.setCompetition(jerseyDTO.getCompetition());
        createdJersey.setBrand(jerseyDTO.getBrand());
        createdJersey.setColor(jerseyDTO.getColor());
        createdJersey.setSize(jerseyDTO.getSize());
        createdJersey.setCondition(jerseyDTO.getCondition());
        createdJersey.setCategory(jerseyDTO.getCategory());
        createdJersey.setAcquisitionDate(jerseyDTO.getAcquisitionDate());
        createdJersey.setBuyPrice(jerseyDTO.getBuyPrice());
        createdJersey.setDeleted(false);

        // Mocking the behavior of jerseyRepository.save
        when(jerseyRepository.save(any(Jersey.class))).thenReturn(createdJersey);

        // Calling the service method
        Jersey result = jerseyService.createJersey(jerseyDTO);

        // Asserting the result
        assertNotNull(result);
        assertEquals(jerseyDTO.getClubName(), result.getClubName());
        assertEquals(jerseyDTO.getPlayerName(), result.getPlayerName());
        assertEquals(jerseyDTO.getNumber(), result.getNumber());
        assertEquals(jerseyDTO.getSeason(), result.getSeason());
        assertEquals(jerseyDTO.getCompetition(), result.getCompetition());
        assertEquals(jerseyDTO.getBrand(), result.getBrand());
        assertEquals(jerseyDTO.getColor(), result.getColor());
        assertEquals(jerseyDTO.getSize(), result.getSize());
        assertEquals(jerseyDTO.getCondition(), result.getCondition());
        assertEquals(jerseyDTO.getCategory(), result.getCategory());
        assertEquals(jerseyDTO.getAcquisitionDate(), result.getAcquisitionDate());
        assertEquals(jerseyDTO.getBuyPrice(), result.getBuyPrice());
        assertFalse(result.getDeleted());
    }
    @Test
    void getAllJerseys_Success() {
        // Mocking a list of jerseys
        List<Jersey> jerseys = new ArrayList<>();
        jerseys.add(new Jersey());
        jerseys.add(new Jersey());
        jerseys.add(new Jersey());

        // Mocking the behavior of jerseyRepository.findAllByDeletedIsFalse
        when(jerseyRepository.findAllByDeletedIsFalse()).thenReturn(jerseys);

        // Calling the service method
        List<Jersey> result = jerseyService.getAllJerseys();

        // Asserting the result size
        assertEquals(jerseys.size(), result.size());

        // Verifying that jerseyRepository.findAllByDeletedIsFalse was called
        verify(jerseyRepository).findAllByDeletedIsFalse();
    }

    @Test
    void getJerseyById_Success() {
        // Mocking jersey ID
        UUID jerseyId = UUID.randomUUID();

        // Mocking the found jersey
        Jersey jersey = new Jersey();
        jersey.setId(jerseyId);

        // Mocking the behavior of jerseyRepository.findById
        when(jerseyRepository.findById(jerseyId)).thenReturn(Optional.of(jersey));

        // Calling the service method
        Jersey result = jerseyService.getJerseyById(jerseyId);

        // Asserting the result
        assertNotNull(result);
        assertEquals(jerseyId, result.getId());

        // Verifying that jerseyRepository.findById was called
        verify(jerseyRepository).findById(jerseyId);
    }

    @Test
    void getJerseyById_JerseyNotFound() {
        // Mocking jersey ID
        UUID jerseyId = UUID.randomUUID();

        // Mocking optional jersey as empty
        when(jerseyRepository.findById(jerseyId)).thenReturn(Optional.empty());

        // Calling the service method and expecting JerseyNotFoundException
        assertThrows(JerseyNotFoundException.class, () -> jerseyService.getJerseyById(jerseyId));

        // Verifying that jerseyRepository.findById was called
        verify(jerseyRepository).findById(jerseyId);
    }

    @Test
    void deleteJersey_Success() {
        // Mocking jersey ID
        UUID jerseyId = UUID.randomUUID();

        // Mocking the found jersey
        Jersey jersey = new Jersey();
        jersey.setId(jerseyId);

        // Mocking the behavior of jerseyRepository.findById
        when(jerseyRepository.findById(jerseyId)).thenReturn(Optional.of(jersey));

        // Calling the service method
        jerseyService.deleteJersey(jerseyId);

        // Verifying that jersey.setDeleted(true) was called
        assertTrue(jersey.getDeleted());

        // Verifying that jerseyRepository.save was called
        verify(jerseyRepository).save(jersey);
    }

    @Test
    void deleteJersey_JerseyNotFound() {
        // Mocking jersey ID
        UUID jerseyId = UUID.randomUUID();

        // Mocking optional jersey as empty
        when(jerseyRepository.findById(jerseyId)).thenReturn(Optional.empty());

        // Calling the service method and expecting JerseyNotFoundException
        assertThrows(JerseyNotFoundException.class, () -> jerseyService.deleteJersey(jerseyId));

        // Verifying that jerseyRepository.findById was called
        verify(jerseyRepository).findById(jerseyId);

        // Verifying that jerseyRepository.save was not called
        verify(jerseyRepository, never()).save(any());
    }
}
