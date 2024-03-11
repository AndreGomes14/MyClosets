package com.my.Closet.service;

import com.my.Closet.entity.Image;
import com.my.Closet.entity.User;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.repository.JerseyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.entity.Jersey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class JerseyService {

    private final JerseyRepository jerseyRepository;
    private final ImageService imageService;
    @Autowired
    public JerseyService(JerseyRepository jerseyRepository, ImageService imageService) {
        this.jerseyRepository = jerseyRepository;
        this.imageService = imageService;

    }

    public Jersey createJersey(JerseyDTO jerseyDTO) {
        Jersey jersey = Jersey.builder()
                .clubName(jerseyDTO.getClubName())
                .playerName(jerseyDTO.getPlayerName())
                .number(jerseyDTO.getNumber())
                .season(jerseyDTO.getSeason())
                .competition(jerseyDTO.getCompetition())
                .brand(jerseyDTO.getBrand())
                .color(jerseyDTO.getColor())
                .size(jerseyDTO.getSize())
                .patches(jerseyDTO.getPatches())
                .condition(jerseyDTO.getCondition())
                .category(jerseyDTO.getCategory())
                .acquisitionDate(jerseyDTO.getAcquisitionDate())
                .buyPrice(jerseyDTO.getBuyPrice())
                .sellPrice(jerseyDTO.getSellPrice())
                .build();
        return jerseyRepository.save(jersey);
    }

    public void addPhotosToJersey(UUID jerseyId, List<String> photoUrls) throws IOException {
        Jersey jersey = jerseyRepository.findById(jerseyId)
                .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));

        List<Image> photos = new ArrayList<>();
        for (String url : photoUrls) {
            Image image = imageService.createImageFromUrl(url);
            photos.add(image);
        }

        jersey.getPhotos().addAll(photos);

        jerseyRepository.save(jersey);
    }

    public List<Jersey> getAllJerseys() {
        return jerseyRepository.findAll();
    }

    public Jersey getJerseyById(UUID jerseyId) {
        return jerseyRepository.findById(jerseyId)
                .orElseThrow(() -> new JerseyNotFoundException("Jersey not found with ID: " + jerseyId));
    }
    public void deleteJersey(UUID jerseyId) {
        jerseyRepository.deleteById(jerseyId);
    }
}
