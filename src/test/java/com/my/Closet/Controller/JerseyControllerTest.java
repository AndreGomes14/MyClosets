package com.my.Closet.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.controller.ClosetController;
import com.my.Closet.controller.JerseyController;
import com.my.Closet.entity.Jersey;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.service.JerseyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class JerseyControllerTest {

    @Mock
    private JerseyService jerseyService;

    @InjectMocks
    private JerseyController jerseyController;

    @Test
    void testCreateJersey_Success() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        Jersey createdJersey = new Jersey(); // You should populate this object with sample data
        when(jerseyService.createJersey(jerseyDTO)).thenReturn(createdJersey);

        // When
        ResponseEntity<Jersey> responseEntity = jerseyController.createJersey(jerseyDTO);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdJersey, responseEntity.getBody());
    }

    @Test
    void testCreateJersey_Exception() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        when(jerseyService.createJersey(jerseyDTO)).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<Jersey> responseEntity = jerseyController.createJersey(jerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllJerseys_Success() {
        // Given
        List<Jersey> expectedJerseys = new ArrayList<>(); // You should populate this list with sample data
        when(jerseyService.getAllJerseys()).thenReturn(expectedJerseys);

        // When
        ResponseEntity<List<Jersey>> responseEntity = jerseyController.getAllJerseys();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedJerseys, responseEntity.getBody());
    }

    @Test
    void testGetAllJerseys_Exception() {
        // Given
        when(jerseyService.getAllJerseys()).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<List<Jersey>> responseEntity = jerseyController.getAllJerseys();

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testGetJerseyById_Success() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        Jersey expectedJersey = new Jersey(); // You should populate this object with sample data
        when(jerseyService.getJerseyById(jerseyDTO.getId())).thenReturn(expectedJersey);

        // When
        ResponseEntity<Jersey> responseEntity = jerseyController.getJerseyById(jerseyDTO);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedJersey, responseEntity.getBody());
    }

    @Test
    void testGetJerseyById_JerseyNotFoundException() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        when(jerseyService.getJerseyById(jerseyDTO.getId())).thenThrow(new JerseyNotFoundException("Test exception"));

        // When
        ResponseEntity<Jersey> responseEntity = jerseyController.getJerseyById(jerseyDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetJerseyById_Exception() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        when(jerseyService.getJerseyById(jerseyDTO.getId())).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<Jersey> responseEntity = jerseyController.getJerseyById(jerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteJersey_Success() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data

        // When
        ResponseEntity<Void> responseEntity = jerseyController.deleteJersey(jerseyDTO);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(jerseyService, times(1)).deleteJersey(jerseyDTO.getId());
    }

    @Test
    void testDeleteJersey_JerseyNotFoundException() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        doThrow(new JerseyNotFoundException("Test exception")).when(jerseyService).deleteJersey(jerseyDTO.getId());

        // When
        ResponseEntity<Void> responseEntity = jerseyController.deleteJersey(jerseyDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteJersey_Exception() {
        // Given
        JerseyDTO jerseyDTO = new JerseyDTO(); // You should populate this DTO with sample data
        doThrow(new RuntimeException("Test exception")).when(jerseyService).deleteJersey(jerseyDTO.getId());

        // When
        ResponseEntity<Void> responseEntity = jerseyController.deleteJersey(jerseyDTO);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
