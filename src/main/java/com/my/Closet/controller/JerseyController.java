package com.my.Closet.controller;

import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Jersey;
import com.my.Closet.entity.User;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.service.JerseyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/jersey")
@Slf4j
public class JerseyController {


    private final JerseyService jerseyService;

    @Autowired
    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }
    @PostMapping("/createJersey")
    public ResponseEntity<Jersey> createJersey(@RequestBody JerseyDTO jerseyDTO) {
        try {
            log.info("Creating jersey with data: {}", jerseyDTO);
            Jersey createdJersey = jerseyService.createJersey(jerseyDTO);
            log.info("Jersey created successfully: {}", createdJersey);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJersey);
        } catch (Exception e) {
            log.error("Error occurred while creating jersey", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllJerseys")
    public ResponseEntity<List<Jersey>> getAllJerseys() {
        try {
            log.info("Fetching all jerseys");
            List<Jersey> jerseys = jerseyService.getAllJerseys();
            log.info("Retrieved {} jerseys", jerseys.size());
            return ResponseEntity.ok(jerseys);
        } catch (Exception e) {
            log.error("Error occurred while fetching all jerseys", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getJerseyByID")
    public ResponseEntity<Jersey> getJerseyById(@RequestBody JerseyDTO jerseyId) {
        try {
            log.info("Fetching jersey with ID: {}", jerseyId);
            Jersey jersey = jerseyService.getJerseyById(jerseyId.getId());
            log.info("Jersey found: {}", jersey);
            return ResponseEntity.ok(jersey);
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", jerseyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while fetching jersey with ID: {}", jerseyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/deleteJersey")
    public ResponseEntity<Void> deleteJersey(@RequestBody JerseyDTO jerseyId) {
        try {
            log.info("Deleting jersey with ID: {}", jerseyId.getId());
            jerseyService.deleteJersey(jerseyId.getId());
            log.info("Jersey deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", jerseyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting jersey with ID: {}", jerseyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
