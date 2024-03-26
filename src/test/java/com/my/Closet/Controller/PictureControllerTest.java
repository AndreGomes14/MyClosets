package com.my.Closet.Controller;

import com.my.Closet.controller.PictureController;
import com.my.Closet.entity.Picture;
import com.my.Closet.service.PictureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PictureControllerTest {
    @Mock
    private PictureService pictureService;

    @InjectMocks
    private PictureController pictureController;

    @Test
    void uploadPicture_Success() throws Exception {
        // Mocking UUID and MultipartFile
        UUID id = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);

        // Mocking PictureService's response
        Picture picture = new Picture();
        ResponseEntity<Picture> expectedResponse = ResponseEntity.ok(picture);
        when(pictureService.uploadImage(id, file)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<Picture> response = pictureController.uploadPicture(id, file);

        // Verifying that the controller returns the expected response
        assertEquals(expectedResponse, response);

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).uploadImage(id, file);
    }

    @Test
    void uploadPicture_Exception() throws Exception {
        // Mocking UUID and MultipartFile
        UUID id = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);

        // Mocking PictureService's response to simulate an exception
        when(pictureService.uploadImage(id, file)).thenThrow(new RuntimeException());

        // Calling the controller method
        ResponseEntity<Picture> response = pictureController.uploadPicture(id, file);

        // Verifying that the controller returns an internal server error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).uploadImage(id, file);
    }

    @Test
    void getAllPicturesByJerseyId_Success() throws Exception {
        // Mocking UUID
        UUID jerseyId = UUID.randomUUID();

        // Mocking PictureService's response
        List<Picture> pictures = Collections.emptyList();
        ResponseEntity<List<Picture>> expectedResponse = ResponseEntity.ok(pictures);
        when(pictureService.getAllPicturesByJerseyId(jerseyId)).thenReturn(pictures);

        // Calling the controller method
        ResponseEntity<List<Picture>> response = pictureController.getAllPicturesByJerseyId(jerseyId);

        // Verifying that the controller returns the expected response
        assertEquals(expectedResponse, response);

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).getAllPicturesByJerseyId(jerseyId);
    }

    @Test
    void getAllPicturesByJerseyId_Exception() throws Exception {
        // Mocking UUID
        UUID jerseyId = UUID.randomUUID();

        // Mocking PictureService's response to simulate an exception
        when(pictureService.getAllPicturesByJerseyId(jerseyId)).thenThrow(new RuntimeException());

        // Calling the controller method
        ResponseEntity<List<Picture>> response = pictureController.getAllPicturesByJerseyId(jerseyId);

        // Verifying that the controller returns an internal server error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).getAllPicturesByJerseyId(jerseyId);
    }

    @Test
    void getPictureById_Success() throws Exception {
        // Mocking UUID
        UUID id = UUID.randomUUID();

        // Mocking PictureService's response
        Picture picture = new Picture();
        ResponseEntity<Picture> expectedResponse = ResponseEntity.ok(picture);
        when(pictureService.getPictureById(id)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<Picture> response = pictureController.getPictureById(id);

        // Verifying that the controller returns the expected response
        assertEquals(expectedResponse, response);

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).getPictureById(id);
    }

    @Test
    void getPictureById_Exception() throws Exception {
        // Mocking UUID
        UUID id = UUID.randomUUID();

        // Mocking PictureService's response to simulate an exception
        when(pictureService.getPictureById(id)).thenThrow(new RuntimeException());

        // Calling the controller method
        ResponseEntity<Picture> response = pictureController.getPictureById(id);

        // Verifying that the controller returns an internal server error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).getPictureById(id);
    }

    @Test
    void deletePictureById_Success() throws Exception {
        // Mocking UUID
        UUID id = UUID.randomUUID();

        // Mocking PictureService's response
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        when(pictureService.deletePictureById(id)).thenReturn(expectedResponse);

        // Calling the controller method
        ResponseEntity<Void> response = pictureController.deletePictureById(id);

        // Verifying that the controller returns the expected response
        assertEquals(expectedResponse, response);

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).deletePictureById(id);
    }

    @Test
    void deletePictureById_Exception() throws Exception {
        // Mocking UUID
        UUID id = UUID.randomUUID();

        // Mocking PictureService's response to simulate an exception
        when(pictureService.deletePictureById(id)).thenThrow(new RuntimeException());

        // Calling the controller method
        ResponseEntity<Void> response = pictureController.deletePictureById(id);

        // Verifying that the controller returns an internal server error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verifying that PictureService's method was called with the correct parameters
        verify(pictureService).deletePictureById(id);
    }
}
