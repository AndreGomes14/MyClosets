package com.my.Closet.service;

import com.my.Closet.entity.Image;
import com.my.Closet.entity.User;
import com.my.Closet.exception.UserNotFoundException;
import com.my.Closet.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

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

    public Image createImageFromUrl(String url) throws IOException {
        // Carrega os dados da imagem a partir da URL
        byte[] imageData = loadDataFromUrl(url);

        // Cria uma nova instância de Image com os dados carregados
        Image image = new Image();
        image.setData(imageData);
        image.setIsDeleted(false);

        return image;
    }

    private byte[] loadDataFromUrl(String url) throws IOException {
        // Faz uma solicitação HTTP para a URL da imagem e carrega os dados
        URL imageUrl = new URL(url);
        return Files.readAllBytes(Path.of(imageUrl.getFile()));
    }

    public void deleteImage(UUID id) {
        imageRepository.deleteById(id);
    }
}
