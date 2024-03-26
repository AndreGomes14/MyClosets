package com.my.Closet.service;


import com.my.Closet.entity.Picture;
import com.my.Closet.repository.JerseyRepository;
import com.my.Closet.repository.PictureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PictureService {

    Logger log = LoggerFactory.getLogger(PictureService.class);
    @Autowired
    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;

    }

    public ResponseEntity<Picture> uploadImage(UUID id, MultipartFile multipartFile) {
        try {
            Picture picture = new Picture();
            picture.setContent(multipartFile.getBytes());
            Optional<Picture> optionalPicture = pictureRepository.findById(id);
            if (optionalPicture.isPresent()) {
                Picture existingPicture = optionalPicture.get();
                existingPicture.setContent(picture.getContent());
                pictureRepository.save(existingPicture);
                return ResponseEntity.ok(existingPicture);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public List<Picture> getAllPicturesByJerseyId(UUID jerseyId) {
        try {
            return pictureRepository.findAllByJerseyId(jerseyId);
        } catch (Exception e) {
            // Em caso de exceção, retornar uma lista vazia ou lidar com a exceção de outra forma
            e.printStackTrace();
            return Collections.emptyList(); // Retorna uma lista vazia em caso de exceção
        }
    }

    public ResponseEntity<Picture> getPictureById(UUID id) {
        try {
            Optional<Picture> optionalPicture = pictureRepository.findById(id);
            return optionalPicture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    public ResponseEntity<Void> deletePictureById(UUID id) {
        try {
            Optional<Picture> optionalPicture = pictureRepository.findById(id);
            if (optionalPicture.isPresent()) {
                pictureRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
