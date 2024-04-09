package com.my.Closet.Service;

import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.model.WishJersey;
import com.my.Closet.repository.WishJerseyRepository;
import com.my.Closet.service.WishJerseyService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishJerseyServiceTest {

    @Mock
    private WishJerseyRepository wishJerseyRepository;

    @InjectMocks
    private WishJerseyService wishJerseyService;

    @Test
    void testCreateWishJersey_Success() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        WishJersey wishJerseyEntity = new WishJersey(/* initialize with expected data */);

        // Mocking repository behavior
        when(wishJerseyRepository.save(any(WishJersey.class))).thenReturn(wishJerseyEntity);

        // When
        WishJersey createdWishJersey = wishJerseyService.createWishJersey(wishJerseyDTO);

        // Then
        assertNotNull(createdWishJersey);
        // Add more assertions to verify the correctness of createdWishJersey if needed
    }

    @Test
    void testCreateWishJersey_Failure() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);

        // Mocking repository behavior to throw an exception
        when(wishJerseyRepository.save(any(WishJersey.class))).thenThrow(new RuntimeException("Test exception"));

        // When & Then
        assertThrows(ServiceException.class, () -> {
            wishJerseyService.createWishJersey(wishJerseyDTO);
        });
    }

    @Test
    void testConvertToWishJerseyEntity() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO();
        wishJerseyDTO.setClubName("Club A");
        wishJerseyDTO.setPlayerName("Player X");
        wishJerseyDTO.setNumber("10");
        wishJerseyDTO.setSeason("2021/2022");
        wishJerseyDTO.setCompetition("Premier League");
        wishJerseyDTO.setBrand("Nike");
        wishJerseyDTO.setColor("Red");
        wishJerseyDTO.setSize("M");
        // Set other properties as needed

        // When
        WishJerseyService wishJerseyService = new WishJerseyService(null); // Pass null as repository is not used
        WishJersey wishJerseyEntity = wishJerseyService.convertToWishJerseyEntity(wishJerseyDTO);

        // Then
        assertNotNull(wishJerseyEntity);
        assertEquals("Club A", wishJerseyEntity.getClubName());
        assertEquals("Player X", wishJerseyEntity.getPlayerName());
        assertEquals("10", wishJerseyEntity.getNumber());
        assertEquals("2021/2022", wishJerseyEntity.getSeason());
        assertEquals("Premier League", wishJerseyEntity.getCompetition());
        assertEquals("Nike", wishJerseyEntity.getBrand());
        assertEquals("Red", wishJerseyEntity.getColor());
        assertEquals("M", wishJerseyEntity.getSize());
        // Add more assertions to verify the correctness of wishJerseyEntity if needed
    }

    @Test
    void testGetAllWishJerseys() {
        // Given
        WishJersey wishJersey1 = new WishJersey(/* Initialize with required data */);
        WishJersey wishJersey2 = new WishJersey(/* Initialize with required data */);
        List<WishJersey> wishJerseys = Arrays.asList(wishJersey1, wishJersey2);

        // Mocking repository behavior
        Mockito.when(wishJerseyRepository.findAll()).thenReturn(wishJerseys);

        // When
        List<WishJersey> returnedWishJerseys = wishJerseyService.getAllWishJerseys();

        // Then
        assertNotNull(returnedWishJerseys);
        assertEquals(2, returnedWishJerseys.size());
    }

    @Test
    void testDeleteWishJersey() {
        // Given
        UUID id = UUID.randomUUID();

        // Mocking repository behavior
        Mockito.when(wishJerseyRepository.existsById(id)).thenReturn(true);

        // When
        wishJerseyService.deleteWishJersey(id);

        // Then
        Mockito.verify(wishJerseyRepository, Mockito.times(1)).deleteById(id);
    }
}
