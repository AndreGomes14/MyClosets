package com.my.Closet.Controller;

import com.my.Closet.DTO.WishClosetDTO;
import com.my.Closet.DTO.WishClosetJerseyDTO;
import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.controller.WishClosetController;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.exception.WishClosetCreationException;
import com.my.Closet.exception.WishClosetNotFoundException;
import com.my.Closet.service.WishClosetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishClosetControllerTest {

    @Mock
    private WishClosetService wishClosetService;

    @InjectMocks
    private WishClosetController wishClosetController;

    @Test
    void testCreateWishCloset_Success() throws WishClosetCreationException {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class); // Mock the service
        WishClosetController wishClosetController = new WishClosetController(wishClosetService); // Create controller instance
        WishClosetDTO createWishClosetDTO = new WishClosetDTO(/* initialize with required data */);
        WishClosetDTO createdWishClosetDTO = new WishClosetDTO(/* initialize with expected data */);
        when(wishClosetService.createWishCloset(createWishClosetDTO)).thenReturn(createdWishClosetDTO);

        // When
        ResponseEntity<WishClosetDTO> responseEntity = wishClosetController.createWishCloset(createWishClosetDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdWishClosetDTO, responseEntity.getBody());
        verify(wishClosetService, times(1)).createWishCloset(createWishClosetDTO);
    }

    @Test
    void testCreateWishCloset_InternalServerError() throws WishClosetCreationException {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class); // Mock the service
        WishClosetController wishClosetController = new WishClosetController(wishClosetService); // Create controller instance
        WishClosetDTO createWishClosetDTO = new WishClosetDTO(/* initialize with required data */);
        when(wishClosetService.createWishCloset(createWishClosetDTO)).thenThrow(new RuntimeException());

        // When
        ResponseEntity<WishClosetDTO> responseEntity = wishClosetController.createWishCloset(createWishClosetDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() == null || responseEntity.getBody().isEmpty());
        verify(wishClosetService, times(1)).createWishCloset(createWishClosetDTO);
    }

    @Test
    void testGetAllClosets_Success() {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        List<WishClosetDTO> closets = new ArrayList<>();
        // Add some test data to the list
        closets.add(new WishClosetDTO(/* initialize with test data */));
        closets.add(new WishClosetDTO(/* initialize with test data */));
        when(wishClosetService.getAllClosets()).thenReturn(closets);

        // When
        ResponseEntity<List<WishClosetDTO>> responseEntity = wishClosetController.getAllClosets();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(closets.size(), Objects.requireNonNull(responseEntity.getBody()).size());

    }

    @Test
    void testGetAllClosets_InternalServerError() {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        when(wishClosetService.getAllClosets()).thenThrow(new RuntimeException());

        // When
        ResponseEntity<List<WishClosetDTO>> responseEntity = wishClosetController.getAllClosets();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testGetClosetsByUserId_Success() {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        UUID userId = UUID.randomUUID();
        List<WishClosetDTO> closets = new ArrayList<>();
        // Add some test data to the list
        closets.add(new WishClosetDTO(/* initialize with test data */));
        closets.add(new WishClosetDTO(/* initialize with test data */));
        when(wishClosetService.getClosetsByUserId(userId)).thenReturn(closets);
        WishClosetDTO requestDto = new WishClosetDTO();
        requestDto.setId(userId);

        // When
        ResponseEntity<List<WishClosetDTO>> responseEntity = wishClosetController.getClosetsByUserId(requestDto);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(closets.size(), responseEntity.getBody().size());
        // You can further validate the content of the response body if needed
        // For example:
        // assertEquals(closets.get(0), responseEntity.getBody().get(0));
        // assertEquals(closets.get(1), responseEntity.getBody().get(1));
    }

    @Test
    void testGetClosetsByUserId_InternalServerError() {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        UUID userId = UUID.randomUUID();
        when(wishClosetService.getClosetsByUserId(userId)).thenThrow(new RuntimeException());
        WishClosetDTO requestDto = new WishClosetDTO();
        requestDto.setId(userId);

        // When
        ResponseEntity<List<WishClosetDTO>> responseEntity = wishClosetController.getClosetsByUserId(requestDto);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteCloset_Success() throws WishClosetNotFoundException {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        WishClosetDTO closetId = new WishClosetDTO();
        closetId.setId(closetId.getId());
        doNothing().when(wishClosetService).deleteCloset(closetId.getId());

        // When
        ResponseEntity<Void> responseEntity = wishClosetController.deleteCloset(closetId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(wishClosetService).deleteCloset(closetId.getId());
    }

    @Test
    void testDeleteCloset_NotFound() throws WishClosetNotFoundException {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        WishClosetDTO closetId = new WishClosetDTO();
        closetId.setId(closetId.getId());
        doThrow(WishClosetNotFoundException.class).when(wishClosetService).deleteCloset(closetId.getId());

        // When
        ResponseEntity<Void> responseEntity = wishClosetController.deleteCloset(closetId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testAddJerseyToCloset_Success() throws WishClosetNotFoundException, JerseyNotFoundException {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        WishClosetJerseyDTO closetWishJerseyDTO = new WishClosetJerseyDTO();
        // Inicialize closetWishJerseyDTO conforme necessário

        doNothing().when(wishClosetService).addJerseyToWishCloset(closetWishJerseyDTO);

        // When
        ResponseEntity<Void> responseEntity = wishClosetController.addJerseyToCloset(closetWishJerseyDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(wishClosetService).addJerseyToWishCloset(closetWishJerseyDTO);
    }

    @Test
    void testGetAllWishJerseysByWishClosetId() {
        // Given
        WishClosetService wishClosetService = mock(WishClosetService.class);
        WishClosetController wishClosetController = new WishClosetController(wishClosetService);
        WishClosetDTO wishClosetId = new WishClosetDTO();
        List<WishJerseyDTO> mockJerseys = new ArrayList<>();

        when(wishClosetService.getAllWishJerseysByWishClosetId(wishClosetId.getId())).thenReturn(mockJerseys);

        // When
        ResponseEntity<List<WishJerseyDTO>> responseEntity = wishClosetController.getAllWishJerseysByWishClosetId(wishClosetId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockJerseys, responseEntity.getBody());
        verify(wishClosetService).getAllWishJerseysByWishClosetId(wishClosetId.getId());
    }

}
