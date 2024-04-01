package com.my.Closet.controller;

import com.my.Closet.DTO.*;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.exception.WishClosetCreationException;
import com.my.Closet.exception.WishClosetNotFoundException;
import com.my.Closet.service.WishClosetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishCloset")
@Slf4j
public class WishClosetController {
    private final WishClosetService wishClosetService;

    @Autowired
    public WishClosetController(WishClosetService wishClosetService) {
        this.wishClosetService = wishClosetService;
    }

    @PostMapping("/createWishCloset")
    public ResponseEntity<WishClosetDTO> createWishCloset(@RequestBody WishClosetDTO createWishClosetDTO) {
        try {
            log.info("Creating wish closet: {}", createWishClosetDTO);
            WishClosetDTO wishClosetDTO = wishClosetService.createWishCloset(createWishClosetDTO);
            log.info("Wish closet created successfully: {}", wishClosetDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(wishClosetDTO);
        } catch (Exception e) {
            log.error("Error occurred while creating wish closet: {}", createWishClosetDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (WishClosetCreationException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllClosets")
    public ResponseEntity<List<WishClosetDTO>> getAllClosets() {
        try {
            log.info("Fetching all closets");
            List<WishClosetDTO> closets = wishClosetService.getAllClosets();
            log.info("Retrieved {} closets", closets.size());
            return new ResponseEntity<>(closets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all closets", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getWishClosetByUserID")
    public ResponseEntity<List<WishClosetDTO>> getClosetsByUserId(@RequestBody WishClosetDTO userId) {
        try {
            log.info("Fetching closets for user with ID: {}", userId);
            List<WishClosetDTO> closets = wishClosetService.getClosetsByUserId(userId.getId());
            log.info("Retrieved {} closets for user with ID: {}", closets.size(), userId);
            return ResponseEntity.ok().body(closets);
        } catch (Exception e) {
            log.error("Error occurred while fetching closets for user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteWishCloset")
    public ResponseEntity<Void> deleteCloset(@RequestBody WishClosetDTO closetId) {
        try {
            log.info("Deleting closet with ID: {}", closetId);
            wishClosetService.deleteCloset(closetId.getId());
            log.info("Closet with ID {} deleted successfully", closetId);
            return ResponseEntity.noContent().build();
        } catch (Exception | WishClosetNotFoundException e) {
            log.error("Error occurred while deleting closet with ID: {}", closetId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addJerseysToWishCloset")
    public ResponseEntity<Void> addJerseyToCloset(@RequestBody WishClosetJerseyDTO closetWishJerseyDTO) {
        try {
            log.info("Adding jersey to WishCloset: {}", closetWishJerseyDTO);
            wishClosetService.addJerseyToWishCloset(closetWishJerseyDTO);
            log.info("Jersey added successfully to WishCloset");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (WishClosetNotFoundException e) {
            log.warn("WishCloset not found with ID: {}", closetWishJerseyDTO.getWishClosetDTO().getId());
            return ResponseEntity.notFound().build();
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", closetWishJerseyDTO.getJerseyDTO().getId());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error occurred while adding jersey to WishCloset", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllJerseysByClosetID")
    public ResponseEntity<List<WishJerseyDTO>> getAllWishJerseysByWishClosetId(@RequestBody WishClosetDTO wishClosetId) {
        try {
            List<WishJerseyDTO> jerseys = wishClosetService.getAllWishJerseysByWishClosetId(wishClosetId.getId());
            return ResponseEntity.ok(jerseys);
        } catch (Exception e) {
            log.error("Error occurred while getting jerseys by closet ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
