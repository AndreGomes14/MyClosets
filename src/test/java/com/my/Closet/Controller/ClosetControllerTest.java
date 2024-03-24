package com.my.Closet.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.ClosetJerseyDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.controller.ClosetController;
import com.my.Closet.service.ClosetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ClosetControllerTest {

    @Mock
    private ClosetService closetService;

    @InjectMocks
    private ClosetController closetController;

    @Test
    void testCreateCloset_Success() {
        // Given
        ClosetDTO requestDTO = new ClosetDTO(); // You should create a sample DTO for testing
        ClosetDTO expectedDTO = new ClosetDTO(); // You should define what you expect the service to return
        when(closetService.createCloset(requestDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<ClosetDTO> response = closetController.createCloset(requestDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
        verify(closetService, times(1)).createCloset(requestDTO);
    }

    @Test
    void testCreateCloset_Exception() {
        // Given
        ClosetDTO requestDTO = new ClosetDTO(); // You should create a sample DTO for testing
        when(closetService.createCloset(requestDTO)).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<ClosetDTO> response = closetController.createCloset(requestDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).createCloset(requestDTO);
    }

    @Test
    void testGetAllClosets_Success() {
        // Given
        List<ClosetDTO> expectedClosets = new ArrayList<>(); // You should populate this list with sample DTOs
        when(closetService.getAllClosets()).thenReturn(expectedClosets);

        // When
        ResponseEntity<List<ClosetDTO>> response = closetController.getAllClosets();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClosets, response.getBody());
        verify(closetService, times(1)).getAllClosets();
    }

    @Test
    void testGetAllClosets_Exception() {
        // Given
        when(closetService.getAllClosets()).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<List<ClosetDTO>> response = closetController.getAllClosets();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).getAllClosets();
    }

    @Test
    void testGetClosetsByUserId_Success() {
        // Given
        UUID userId = UUID.randomUUID();
        List<ClosetDTO> expectedClosets = new ArrayList<>(); // You should populate this list with sample DTOs
        when(closetService.getClosetsByUserId(userId)).thenReturn(expectedClosets);

        // When
        ClosetDTO userIdDto = new ClosetDTO();
        userIdDto.setId(userId);
        ResponseEntity<List<ClosetDTO>> response = closetController.getClosetsByUserId(userIdDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClosets, response.getBody());
        verify(closetService, times(1)).getClosetsByUserId(userId);
    }

    @Test
    void testGetClosetsByUserId_Exception() {
        // Given
        UUID userId = UUID.randomUUID();
        ClosetDTO userIdDto = new ClosetDTO();
        userIdDto.setId(userId);
        when(closetService.getClosetsByUserId(userId)).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<List<ClosetDTO>> response = closetController.getClosetsByUserId(userIdDto);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).getClosetsByUserId(userId);
    }
    @Test
    void testDeleteCloset_Success() {
        // Given
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with a sample ID
        doNothing().when(closetService).deleteCloset(closetId.getId());

        // When
        ResponseEntity<Void> response = closetController.deleteCloset(closetId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(closetService, times(1)).deleteCloset(closetId.getId());
    }

    @Test
    void testDeleteCloset_Exception() {
        // Given
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with a sample ID
        doThrow(new RuntimeException("Test exception")).when(closetService).deleteCloset(closetId.getId());

        // When
        ResponseEntity<Void> response = closetController.deleteCloset(closetId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).deleteCloset(closetId.getId());
    }

    @Test
    void testAddJerseyToCloset_Success() {
        // Given
        ClosetJerseyDTO closetJerseyDTO = new ClosetJerseyDTO(); // You should populate this DTO with sample data
        doNothing().when(closetService).addJerseyToCloset(closetJerseyDTO);

        // When
        ResponseEntity<Void> response = closetController.addJerseyToCloset(closetJerseyDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(closetService, times(1)).addJerseyToCloset(closetJerseyDTO);
    }

    @Test
    void testAddJerseyToCloset_Exception() {
        // Given
        ClosetJerseyDTO closetJerseyDTO = new ClosetJerseyDTO(); // You should populate this DTO with sample data
        doThrow(new RuntimeException("Test exception")).when(closetService).addJerseyToCloset(closetJerseyDTO);

        // When
        ResponseEntity<Void> response = closetController.addJerseyToCloset(closetJerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).addJerseyToCloset(closetJerseyDTO);
    }

    @Test
    void testGetAllJerseysByClosetId_Success() {
        // Given
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with a sample ID
        List<JerseyDTO> expectedJerseys = new ArrayList<>(); // You should populate this list with sample DTOs
        when(closetService.getAllJerseysByClosetId(closetId.getId())).thenReturn(expectedJerseys);

        // When
        ResponseEntity<List<JerseyDTO>> response = closetController.getAllJerseysByClosetId(closetId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedJerseys, response.getBody());
        verify(closetService, times(1)).getAllJerseysByClosetId(closetId.getId());
    }

    @Test
    void testGetAllJerseysByClosetId_Exception() {
        // Given
        ClosetDTO closetId = new ClosetDTO(); // You should populate this DTO with a sample ID
        when(closetService.getAllJerseysByClosetId(closetId.getId())).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<List<JerseyDTO>> response = closetController.getAllJerseysByClosetId(closetId);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(closetService, times(1)).getAllJerseysByClosetId(closetId.getId());
    }
}
