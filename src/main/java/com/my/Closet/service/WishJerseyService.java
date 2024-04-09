package com.my.Closet.service;

import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.exception.WishJerseyNotFoundException;
import com.my.Closet.model.WishJersey;
import com.my.Closet.repository.WishJerseyRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class WishJerseyService {

    private final WishJerseyRepository wishJerseyRepository;

    @Autowired
    public WishJerseyService(WishJerseyRepository wishJerseyRepository) {
        this.wishJerseyRepository = wishJerseyRepository;
    }

    public WishJersey createWishJersey(WishJerseyDTO wishJerseyDTO) {
        try {
            log.info("Creating WishJersey with data: {}", wishJerseyDTO);
            // Convert WishJerseyDTO to WishJersey entity
            WishJersey wishJersey = convertToWishJerseyEntity(wishJerseyDTO);

            // Save the WishJersey entity
            WishJersey savedWishJersey = wishJerseyRepository.save(wishJersey);
            log.info("WishJersey created successfully: {}", savedWishJersey);
            return savedWishJersey;
        } catch (Exception e) {
            log.error("Error occurred while creating WishJersey", e);
            throw new ServiceException("Failed to create WishJersey", e);
        }
    }

    // Helper method to convert WishJerseyDTO to WishJersey entity
    public WishJersey convertToWishJerseyEntity(WishJerseyDTO wishJerseyDTO) {
        WishJersey wishJersey = new WishJersey();
        wishJersey.setClubName(wishJerseyDTO.getClubName());
        wishJersey.setPlayerName(wishJerseyDTO.getPlayerName());
        wishJersey.setNumber(wishJerseyDTO.getNumber());
        wishJersey.setSeason(wishJerseyDTO.getSeason());
        wishJersey.setCompetition(wishJerseyDTO.getCompetition());
        wishJersey.setBrand(wishJerseyDTO.getBrand());
        wishJersey.setColor(wishJerseyDTO.getColor());
        wishJersey.setSize(wishJerseyDTO.getSize());
        // Set other properties as needed
        return wishJersey;
    }

    public List<WishJersey> getAllWishJerseys() {
        try {
            log.info("Fetching all wish jerseys");
            List<WishJersey> wishJerseys = wishJerseyRepository.findAll();
            log.info("Retrieved {} wish jerseys", wishJerseys.size());
            return wishJerseys;
        } catch (Exception e) {
            log.error("Error occurred while fetching all wish jerseys", e);
            throw new ServiceException("Failed to fetch all wish jerseys", e);
        }
    }

    public WishJersey getWishJerseyById(UUID id) {
        try {
            log.info("Fetching wish jersey with ID: {}", id);
            Optional<WishJersey> optionalWishJersey = wishJerseyRepository.findById(id);
            WishJersey wishJersey = optionalWishJersey.orElseThrow(() -> new WishJerseyNotFoundException("WishJersey not found with ID: " + id));
            log.info("Wish jersey found: {}", wishJersey);
            return wishJersey;
        } catch (WishJerseyNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching wish jersey with ID: {}", id, e);
            throw new ServiceException("Failed to fetch wish jersey with ID: " + id, e);
        }
    }

    public void deleteWishJersey(UUID id) {
        try {
            log.info("Deleting wish jersey with ID: {}", id);
            if (!wishJerseyRepository.existsById(id)) {
                log.warn("Wish jersey not found with ID: {}", id);
                throw new WishJerseyNotFoundException("Not found", id);
            }
            wishJerseyRepository.deleteById(id);
            log.info("Wish jersey deleted successfully");
        } catch (Exception e) {
            log.error("Error occurred while deleting wish jersey with ID: {}", id, e);
            throw e; // Rethrow the exception for handling at the controller level
        }
    }
}
