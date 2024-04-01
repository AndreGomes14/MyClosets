package com.my.Closet.service;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.WishClosetDTO;
import com.my.Closet.DTO.WishClosetJerseyDTO;
import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.exception.*;
import com.my.Closet.model.WishCloset;
import com.my.Closet.model.WishJersey;
import com.my.Closet.repository.WishClosetRepository;
import com.my.Closet.repository.WishJerseyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WishClosetService {

    private final WishClosetRepository wishClosetRepository;
    private final WishJerseyRepository wishJerseyRepository;


    @Autowired
    public WishClosetService(WishClosetRepository wishClosetRepository, WishJerseyRepository wishJerseyRepository) {
        this.wishClosetRepository = wishClosetRepository;
        this.wishJerseyRepository = wishJerseyRepository;
    }

    public WishClosetDTO createWishCloset(WishClosetDTO createWishClosetDTO) throws WishClosetCreationException {
        try {
            // Create a new WishCloset entity using builder
            WishCloset wishCloset = WishCloset.builder()
                    .deleted(false)
                    .build();
            // Save the wish closet entity
            wishCloset = wishClosetRepository.save(wishCloset);
            // Create and return a new WishClosetDTO using builder
            return WishClosetDTO.builder()
                    .id(wishCloset.getId())
                    .deleted(wishCloset.getDeleted())
                    .build();
        } catch (Exception e) {
            // Log the error
            log.error("Error occurred while creating wish closet", e);
            // You can handle the exception based on your requirements
            throw new WishClosetCreationException("Failed to create wish closet", e);
        }
    }


    public List<WishClosetDTO> getAllClosets() {
        try {
            // Fetch all wish closets from the repository
            List<WishCloset> wishClosets = wishClosetRepository.findAll();

            // Convert each WishCloset entity to ClosetDTO using builder pattern
            List<WishClosetDTO> wishClosetDTOs = new ArrayList<>();
            for (WishCloset closet : wishClosets) {
                WishClosetDTO wishClosetDTO = WishClosetDTO.builder()
                        .id(closet.getId())
                        .deleted(closet.getDeleted())
                        .build();
                wishClosetDTOs.add(wishClosetDTO);
            }

            return wishClosetDTOs;
        } catch (Exception e) {
            // Log the error
            log.error("Error occurred while fetching all wish closets", e);
            // You can handle the exception based on your requirements
            throw new WishClosetFetchingException("Failed to fetch all wish closets", e);
        }
    }

    public List<WishClosetDTO> getClosetsByUserId(UUID userId) {
        try {
            // Fetch wish closets by user ID from the repository
            List<WishCloset> closets = wishClosetRepository.findByUserId(userId);

            // Convert each WishCloset entity to ClosetDTO using builder pattern
            List<WishClosetDTO> closetDTOs = new ArrayList<>();
            for (WishCloset closet : closets) {
                WishClosetDTO closetDTO = WishClosetDTO.builder()
                        .id(closet.getId())
                        .deleted(closet.getDeleted())
                        .build();
                closetDTOs.add(closetDTO);
            }

            return closetDTOs;
        } catch (Exception e) {
            // Log the error
            log.error("Error occurred while fetching closets for user with ID: {}", userId, e);
            // You can handle the exception based on your requirements
            throw new WishClosetFetchingException("Failed to fetch closets for user with ID: " + userId, e);
        }
    }

    public void deleteCloset(UUID closetId) throws WishClosetNotFoundException {
        try {
            // Check if the closet exists
            Optional<WishCloset> optionalCloset = wishClosetRepository.findById(closetId);
            if (optionalCloset.isPresent()) {
                // Delete the closet if it exists
                wishClosetRepository.deleteById(closetId);
            } else {
                // If the closet does not exist, throw an exception
                throw new WishClosetNotFoundException("Closet not found with ID: " + closetId);
            }
        } catch (WishClosetNotFoundException e) {
            // Rethrow the exception
            throw e;
        } catch (Exception e) {
            // Log the error
            log.error("Error occurred while deleting closet with ID: {}", closetId, e);
            // You can handle the exception based on your requirements
            throw new WishClosetDeletionException("Failed to delete closet with ID: " + closetId, e);
        }
    }

    public void addJerseyToWishCloset(WishClosetJerseyDTO wishClosetJerseyDTO) throws WishClosetNotFoundException {
        try {
            Optional<WishCloset> optionalWishCloset = wishClosetRepository.findById(wishClosetJerseyDTO.getWishClosetDTO().getId());
            if (optionalWishCloset.isPresent()) {
                WishCloset wishCloset = optionalWishCloset.get();
                Optional<WishJersey> optionalJersey = wishJerseyRepository.findById(wishClosetJerseyDTO.getJerseyDTO().getId());
                if (optionalJersey.isPresent()) {
                    WishJersey jersey = optionalJersey.get();
                    wishCloset.getWishJerseys().add(jersey);
                    jersey.setWishCloset(wishCloset);
                    wishClosetRepository.save(wishCloset);
                    log.info("Jersey added to wish closet. Wish Closet ID: {}, Jersey ID: {}", wishClosetJerseyDTO.getWishClosetDTO().getId(), wishClosetJerseyDTO.getJerseyDTO().getId());
                } else {
                    log.warn("Jersey not found with ID: {}", wishClosetJerseyDTO.getJerseyDTO().getId());
                    throw new JerseyNotFoundException("Jersey not found with ID: " + wishClosetJerseyDTO.getJerseyDTO().getId());
                }
            } else {
                log.warn("Wish closet not found with ID: {}", wishClosetJerseyDTO.getWishClosetDTO().getId());
                throw new WishClosetNotFoundException("Wish closet not found with ID: " + wishClosetJerseyDTO.getWishClosetDTO().getId());
            }
        } catch (Exception | WishClosetNotFoundException e) {
            log.error("Error occurred while adding jersey to wish closet. Wish Closet ID: {}, Jersey ID: {}", wishClosetJerseyDTO.getWishClosetDTO().getId(), wishClosetJerseyDTO.getJerseyDTO().getId(), e);
            throw e;
        }
    }

    public List<WishJerseyDTO> getAllWishJerseysByWishClosetId(UUID wishClosetId) {
        try {
            Optional<WishCloset> wishClosetOptional = wishClosetRepository.findById(wishClosetId);
            if (wishClosetOptional.isPresent()) {
                List<WishJersey> wishJerseys = wishClosetOptional.get().getWishJerseys();
                return wishJerseys.stream()
                        .map(jersey -> WishJerseyDTO.builder()
                                .id(jersey.getId())
                                // Map other attributes as needed
                                .build())
                        .collect(Collectors.toList());
            } else {
                log.warn("Wish closet not found with ID: {}", wishClosetId);
                throw new WishClosetNotFoundException("Wish closet not found with ID: " + wishClosetId);
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching wish jerseys by wish closet ID: {}", wishClosetId, e);
            throw e;
        } catch (WishClosetNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
