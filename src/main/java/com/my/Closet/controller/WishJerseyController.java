package com.my.Closet.controller;

import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.model.Jersey;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.model.WishJersey;
import com.my.Closet.service.JerseyService;
import com.my.Closet.service.WishJerseyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishJersey")
@Slf4j
public class WishJerseyController {

    private final WishJerseyService wishJerseyService;

    @Autowired
    public WishJerseyController(WishJerseyService wishJerseyService) {
        this.wishJerseyService = wishJerseyService;
    }
    @PostMapping("/createWishJersey")
    public ResponseEntity<WishJersey> createWishJersey(@RequestBody WishJerseyDTO wishJerseyDTO) {
        try {
            log.info("Creating WishJersey with data: {}", wishJerseyDTO);
            WishJersey createdJersey = wishJerseyService.createWishJersey(wishJerseyDTO);
            log.info("WishJersey created successfully: {}", createdJersey);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJersey);
        } catch (Exception e) {
            log.error("Error occurred while creating WishJersey", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllJerseys")
    public ResponseEntity<List<WishJersey>> getAllJerseys() {
        try {
            log.info("Fetching all WishJerseys");
            List<WishJersey> jerseys = wishJerseyService.getAllWishJerseys();
            log.info("Retrieved {} WishJerseys", jerseys.size());
            return ResponseEntity.ok(jerseys);
        } catch (Exception e) {
            log.error("Error occurred while fetching all WishJerseys", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getJerseyByID")
    public ResponseEntity<WishJersey> getWishJerseyById(@RequestBody WishJerseyDTO wishJerseyId) {
        try {
            log.info("Fetching jersey with ID: {}", wishJerseyId);
            WishJersey wishJersey = wishJerseyService.getWishJerseyById(wishJerseyId.getId());
            log.info("Jersey found: {}", wishJersey);
            return ResponseEntity.ok(wishJersey);
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", wishJerseyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while fetching jersey with ID: {}", wishJerseyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/deleteWishJersey")
    public ResponseEntity<Void> deleteWishJersey(@RequestBody WishJerseyDTO wishJerseyId) {
        try {
            log.info("Deleting jersey with ID: {}", wishJerseyId.getId());
            wishJerseyService.deleteWishJersey(wishJerseyId.getId());
            log.info("Jersey deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", wishJerseyId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting jersey with ID: {}", wishJerseyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
