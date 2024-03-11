package com.my.Closet.controller;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Closet;
import com.my.Closet.service.ClosetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/closet")
public class ClosetController {

    private final ClosetService closetService;

    @Autowired
    public ClosetController(ClosetService closetService) {
        this.closetService = closetService;
    }

    @PostMapping("/createCloset")
    public ResponseEntity<ClosetDTO> createCloset(@RequestBody ClosetDTO createClosetDTO) {
        ClosetDTO closetDTO = closetService.createCloset(createClosetDTO);
        return new ResponseEntity<>(closetDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAllClosets")
    public ResponseEntity<List<ClosetDTO>> getAllClosets() {
        List<ClosetDTO> closets = closetService.getAllClosets();
        return new ResponseEntity<>(closets, HttpStatus.OK);
    }

    @GetMapping("/getClosetByID")
    public ResponseEntity<List<ClosetDTO>> getClosetsByUserId(@RequestBody UUID userId) {
        List<ClosetDTO> closets = closetService.getClosetsByUserId(userId);
        return ResponseEntity.ok().body(closets);
    }

    @DeleteMapping("/deleteClosetByID}")
    public ResponseEntity<Void> deleteCloset(@RequestBody UUID closetId) {
        closetService.deleteCloset(closetId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addJerseysToCloset")
    public ResponseEntity<Void> addJerseyToCloset(@RequestBody UUID closetId, @RequestBody UUID jerseyId) {
        closetService.addJerseyToCloset(closetId, jerseyId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getAllJerseysByClosetID")
    public ResponseEntity<List<JerseyDTO>> getAllJerseysByClosetId(@PathVariable UUID closetId) {
        List<JerseyDTO> jerseys = closetService.getAllJerseysByClosetId(closetId);
        return ResponseEntity.ok(jerseys);
    }
}
