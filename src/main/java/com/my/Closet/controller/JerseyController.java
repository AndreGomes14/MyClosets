package com.my.Closet.controller;

import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Jersey;
import com.my.Closet.entity.User;
import com.my.Closet.service.JerseyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jersey")
public class JerseyController {


    private final JerseyService jerseyService;

    @Autowired
    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }
    @PostMapping("/createJersey")
    public ResponseEntity<Jersey> createJersey(@RequestBody JerseyDTO jerseyDTO) {
        Jersey createdJersey = jerseyService.createJersey(jerseyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJersey);
    }
    @PostMapping("/addPhotoToJersey")
    public ResponseEntity<Void> addPhotosToJersey(@RequestBody UUID jerseyId, @RequestBody List<String> photoUrls) throws IOException {
        jerseyService.addPhotosToJersey(jerseyId, photoUrls);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getAllJerseys")
    public ResponseEntity<List<Jersey>> getAllJerseys() {
        List<Jersey> jerseys = jerseyService.getAllJerseys();
        return ResponseEntity.ok(jerseys);
    }

    @GetMapping("/getJerseyByID")
    public ResponseEntity<Jersey> getJerseyById(@RequestBody UUID jerseyId) {
        Jersey jersey = jerseyService.getJerseyById(jerseyId);
        return ResponseEntity.ok(jersey);
    }
    @DeleteMapping("/deleteJersey")
    public ResponseEntity<Void> deleteJersey(@RequestBody UUID jerseyId) {
        jerseyService.deleteJersey(jerseyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
