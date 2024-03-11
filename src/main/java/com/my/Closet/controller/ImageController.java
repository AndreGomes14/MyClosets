package com.my.Closet.controller;

import com.my.Closet.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.uploadImage(file);
            return "File uploaded successfully!";
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@RequestBody UUID id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
