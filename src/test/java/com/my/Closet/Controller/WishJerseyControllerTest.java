package com.my.Closet.Controller;

import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.controller.JerseyController;
import com.my.Closet.controller.WishClosetController;
import com.my.Closet.controller.WishJerseyController;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.model.WishJersey;
import com.my.Closet.service.JerseyService;
import com.my.Closet.service.WishJerseyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishJerseyControllerTest {

    private WishJerseyService wishJerseyService;
    private WishJerseyController wishJerseyController;

    @BeforeEach
    void setUp() {
        wishJerseyService = mock(WishJerseyService.class);
        wishJerseyController = new WishJerseyController(wishJerseyService);
    }

    @Test
    void testCreateWishJersey_Success() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        WishJersey createdJersey = new WishJersey(/* initialize with expected data */);
        when(wishJerseyService.createWishJersey(wishJerseyDTO)).thenReturn(createdJersey);

        // When
        ResponseEntity<WishJersey> responseEntity = wishJerseyController.createWishJersey(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdJersey, responseEntity.getBody());
    }

    @Test
    void testCreateWishJersey_InternalServerError() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        when(wishJerseyService.createWishJersey(wishJerseyDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<WishJersey> responseEntity = wishJerseyController.createWishJersey(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }


    @Test
    void testGetAllJerseys_Success() {
        // Given
        List<WishJersey> jerseys = new ArrayList<>();
        jerseys.add(new WishJersey(/* initialize with expected data */));
        jerseys.add(new WishJersey(/* initialize with expected data */));
        when(wishJerseyService.getAllWishJerseys()).thenReturn(jerseys);

        // When
        ResponseEntity<List<WishJersey>> responseEntity = wishJerseyController.getAllJerseys();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(jerseys, responseEntity.getBody());
    }

    @Test
    void testGetAllJerseys_InternalServerError() {
        // Given
        when(wishJerseyService.getAllWishJerseys()).thenThrow(new RuntimeException());

        // When
        ResponseEntity<List<WishJersey>> responseEntity = wishJerseyController.getAllJerseys();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testGetWishJerseyById_Success() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        WishJersey wishJersey = new WishJersey(/* initialize with expected data */);
        when(wishJerseyService.getWishJerseyById(wishJerseyDTO.getId())).thenReturn(wishJersey);

        // When
        ResponseEntity<WishJersey> responseEntity = wishJerseyController.getWishJerseyById(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(wishJersey, responseEntity.getBody());
    }

    @Test
    void testGetWishJerseyById_NotFound() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        when(wishJerseyService.getWishJerseyById(wishJerseyDTO.getId())).thenThrow(new JerseyNotFoundException());

        // When
        ResponseEntity<WishJersey> responseEntity = wishJerseyController.getWishJerseyById(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetWishJerseyById_InternalServerError() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);
        when(wishJerseyService.getWishJerseyById(wishJerseyDTO.getId())).thenThrow(new RuntimeException());

        // When
        ResponseEntity<WishJersey> responseEntity = wishJerseyController.getWishJerseyById(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteWishJersey_Success() {
        // Given
        WishJerseyDTO wishJerseyDTO = new WishJerseyDTO(/* initialize with required data */);

        // When
        ResponseEntity<Void> responseEntity = wishJerseyController.deleteWishJersey(wishJerseyDTO);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(wishJerseyService).deleteWishJersey(wishJerseyDTO.getId());
    }
}
