package com.my.Closet.controller;

import com.my.Closet.model.Picture;
import com.my.Closet.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/picture")
public class PictureController {

    Logger log = LoggerFactory.getLogger(PictureController.class);
    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload")
    public ResponseEntity<Picture> uploadPicture(@RequestBody UUID id, @RequestParam("file") MultipartFile file) {
        try {
            return pictureService.uploadImage(id, file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getPicturesByJerseyID")
    public ResponseEntity<List<Picture>> getAllPicturesByJerseyId(@RequestBody UUID jerseyId) {
        try {
            List<Picture> pictures = pictureService.getAllPicturesByJerseyId(jerseyId);
            return ResponseEntity.ok(pictures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getPictureByID")
    public ResponseEntity<Picture> getPictureById(@RequestBody UUID id) {
        try {
            return pictureService.getPictureById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteImageByID")
    public ResponseEntity<Void> deletePictureById(@PathVariable UUID id) {
        try {
            return pictureService.deletePictureById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

