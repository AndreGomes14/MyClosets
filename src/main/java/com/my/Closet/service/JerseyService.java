package com.my.Closet.service;

import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.exception.JerseyServiceException;
import com.my.Closet.repository.JerseyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Jersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class JerseyService {

    private final JerseyRepository jerseyRepository;
    @Autowired
    public JerseyService(JerseyRepository jerseyRepository) {
        this.jerseyRepository = jerseyRepository;
    }

    public Jersey createJersey(JerseyDTO jerseyDTO) {
        try {
            log.info("Creating jersey with data: {}", jerseyDTO);
            Jersey jersey = Jersey.builder()
                    .clubName(jerseyDTO.getClubName())
                    .playerName(jerseyDTO.getPlayerName())
                    .number(jerseyDTO.getNumber())
                    .season(jerseyDTO.getSeason())
                    .competition(jerseyDTO.getCompetition())
                    .brand(jerseyDTO.getBrand())
                    .color(jerseyDTO.getColor())
                    .size(jerseyDTO.getSize())
                    .condition(jerseyDTO.getCondition())
                    .category(jerseyDTO.getCategory())
                    .acquisitionDate(jerseyDTO.getAcquisitionDate())
                    .buyPrice(jerseyDTO.getBuyPrice())
                    .deleted(false)
                    .build();
            Jersey createdJersey = jerseyRepository.save(jersey);
            log.info("Jersey created successfully: {}", createdJersey);
            return createdJersey;
        } catch (Exception e) {
            log.error("Error occurred while creating jersey", e);
            throw new JerseyServiceException("Failed to create jersey", e);
        }
    }

    public List<Jersey> getAllJerseys() {
        try {
            log.info("Fetching all jerseys.");
            List<Jersey> jerseys = jerseyRepository.findAllByDeletedIsFalse();
            log.info("Fetched {} jerseys successfully.", jerseys.size());
            return jerseys;
        } catch (Exception e) {
            log.error("Error occurred while fetching jerseys.", e);
            throw e;
        }
    }

    public Jersey getJerseyById(UUID jerseyId) {
        try {
            log.info("Fetching jersey with ID: {}", jerseyId);
            Jersey jersey = jerseyRepository.findById(jerseyId)
                    .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));
            log.info("Jersey found: {}", jersey);
            return jersey;
        } catch (JerseyNotFoundException e) {
            log.warn("Jersey not found with ID: {}", jerseyId);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching jersey with ID: {}", jerseyId, e);
            throw e;
        }
    }
    public void deleteJersey(UUID jerseyId) {
        try {
            log.info("Soft deleting jersey with ID: {}", jerseyId);
            Jersey jersey = jerseyRepository.findById(jerseyId)
                    .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));
            jersey.setDeleted(true);
            jerseyRepository.save(jersey);
            log.info("Jersey soft deleted successfully");
        } catch (Exception e) {
            log.error("Error occurred while soft deleting jersey with ID: {}", jerseyId, e);
            throw e;
        }
    }
}
