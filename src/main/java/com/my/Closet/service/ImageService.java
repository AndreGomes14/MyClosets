package com.my.Closet.service;

import com.my.Closet.entity.Image;
import com.my.Closet.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void uploadImage(MultipartFile file) throws Exception {
        try {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setData(file.getBytes());
            imageRepository.save(image);
        } catch (Exception e) {
            throw new Exception("Failed to upload file: " + e.getMessage());
        }
    }
}
