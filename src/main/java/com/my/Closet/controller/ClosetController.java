package com.my.Closet.controller;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.ClosetJerseyDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.service.ClosetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/closet")
@Slf4j
public class ClosetController {

    private final ClosetService closetService;

    @Autowired
    public ClosetController(ClosetService closetService) {
        this.closetService = closetService;
    }

    @PostMapping("/createCloset")
    public ResponseEntity<ClosetDTO> createCloset(@RequestBody ClosetDTO createClosetDTO) {
        try {
            log.info("Creating closet: {}", createClosetDTO);
            ClosetDTO closetDTO = closetService.createCloset(createClosetDTO);
            log.info("Closet created successfully: {}", closetDTO);
            return new ResponseEntity<>(closetDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating closet: {}", createClosetDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllClosets")
    public ResponseEntity<List<ClosetDTO>> getAllClosets() {
        try {
            log.info("Fetching all closets");
            List<ClosetDTO> closets = closetService.getAllClosets();
            log.info("Retrieved {} closets", closets.size());
            return new ResponseEntity<>(closets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred while fetching all closets", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getClosetByUserID")
    public ResponseEntity<List<ClosetDTO>> getClosetsByUserId(@RequestBody ClosetDTO userId) {
        try {
            log.info("Fetching closets for user with ID: {}", userId);
            List<ClosetDTO> closets = closetService.getClosetsByUserId(userId.getId());
            log.info("Retrieved {} closets for user with ID: {}", closets.size(), userId);
            return ResponseEntity.ok().body(closets);
        } catch (Exception e) {
            log.error("Error occurred while fetching closets for user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteCloset")
    public ResponseEntity<Void> deleteCloset(@RequestBody ClosetDTO closetId) {
        try {
            log.info("Deleting closet with ID: {}", closetId);
            closetService.deleteCloset(closetId.getId());
            log.info("Closet with ID {} deleted successfully", closetId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting closet with ID: {}", closetId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addJerseysToCloset")
    public ResponseEntity<Void> addJerseyToCloset(@RequestBody ClosetJerseyDTO closetJerseyDTO) {
        try {
            closetService.addJerseyToCloset(closetJerseyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Error occurred while adding jersey to closet", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllJerseysByClosetID")
    public ResponseEntity<List<JerseyDTO>> getAllJerseysByClosetId(@RequestBody ClosetDTO closetId) {
        try {
            List<JerseyDTO> jerseys = closetService.getAllJerseysByClosetId(closetId.getId());
            return ResponseEntity.ok(jerseys);
        } catch (Exception e) {
            log.error("Error occurred while getting jerseys by closet ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
