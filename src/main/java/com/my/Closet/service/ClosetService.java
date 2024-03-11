package com.my.Closet.service;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.DTO.UserDTO;
import com.my.Closet.entity.Closet;
import com.my.Closet.entity.Jersey;
import com.my.Closet.entity.User;
import com.my.Closet.exception.ClosetNotFoundException;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.JerseyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClosetService {
    private final ClosetRepository closetRepository;
    private final JerseyRepository jerseyRepository;

    @Autowired
    public ClosetService(ClosetRepository closetRepository, JerseyRepository jerseyRepository) {
        this.closetRepository = closetRepository;
        this.jerseyRepository = jerseyRepository;
    }

    public ClosetDTO createCloset(ClosetDTO createClosetDTO) {
        Closet closet = Closet.builder()
                .id(UUID.randomUUID()) // Gerar um novo UUID para o closet
                .name(createClosetDTO.getName())
                .closetType(createClosetDTO.getClosetType())
                .user(User.builder()
                        .id(createClosetDTO.getUser().getId())
                        .username(createClosetDTO.getUser().getUsername())
                        .email(createClosetDTO.getUser().getEmail())
                        .mobilePhone(createClosetDTO.getUser().getMobilePhone())
                        .isDeleted(false) // Por padrão, o usuário não está deletado
                        .build())
                .isDeleted(false) // Por padrão, o closet não está deletado
                .build();
        Closet savedCloset = closetRepository.save(closet);
        return ClosetDTO.builder()
                .id(savedCloset.getId())
                .name(savedCloset.getName())
                .closetType(savedCloset.getClosetType())
                .user(UserDTO.builder()
                        .id(savedCloset.getUser().getId())
                        .username(savedCloset.getUser().getUsername())
                        .email(savedCloset.getUser().getEmail())
                        .mobilePhone(savedCloset.getUser().getMobilePhone())
                        .isDeleted(savedCloset.getUser().getIsDeleted())
                        .build())
                .isDeleted(savedCloset.getIsDeleted())
                .build();
    }

    public List<ClosetDTO> getAllClosets() {
        List<Closet> closets = closetRepository.findAllByDeletedIsFalse();
        List<ClosetDTO> closetDTOs = new ArrayList<>();
        for (Closet closet : closets) {
            closetDTOs.add(convertToDTO(closet));
        }
        return closetDTOs;
    }

    public List<ClosetDTO> getClosetsByUserId(UUID userId) {
        List<Closet> closets = closetRepository.findByUserIdAndDeletedIsFalse(userId);
        List<ClosetDTO> closetDTOs = new ArrayList<>();
        for (Closet closet : closets) {
            closetDTOs.add(convertToDTO(closet));
        }
        return closetDTOs;
    }

    public void deleteCloset(UUID closetId) {
        Optional<Closet> optionalCloset = closetRepository.findById(closetId);
        if (optionalCloset.isPresent()) {
            Closet closet = optionalCloset.get();
            closet.setIsDeleted(true);
            closetRepository.save(closet);
        } else {
            throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
        }
    }

    public void addJerseyToCloset(UUID closetId, UUID jerseyId) {
        Optional<Closet> optionalCloset = closetRepository.findById(closetId);
        if (optionalCloset.isPresent()) {
            Closet closet = optionalCloset.get();
            Optional<Jersey> optionalJersey = jerseyRepository.findById(jerseyId);
            if (optionalJersey.isPresent()) {
                Jersey jersey = optionalJersey.get();
                closet.getJerseys().add(jersey);
                closetRepository.save(closet);
            } else {
                throw new JerseyNotFoundException("Jersey not found with ID: " + jerseyId);
            }
        } else {
            throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
        }
    }

    public List<JerseyDTO> getAllJerseysByClosetId(UUID closetId) {
        Optional<Closet> closetOptional = closetRepository.findById(closetId);
        if (closetOptional.isPresent()) {
            List<Jersey> jerseys = closetOptional.get().getJerseys();
            return jerseys.stream()
                    .map(jersey -> JerseyDTO.builder()
                            .id(jersey.getId())
                            // Mapear outros atributos conforme necessário
                            .build())
                    .collect(Collectors.toList());
        } else {
            throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
        }
    }

    private ClosetDTO convertToDTO(Closet closet) {
        return ClosetDTO.builder()
                .id(closet.getId())
                .name(closet.getName())
                .closetType(closet.getClosetType())
                .user(UserDTO.builder()
                        .id(closet.getUser().getId())
                        .username(closet.getUser().getUsername())
                        .email(closet.getUser().getEmail())
                        .mobilePhone(closet.getUser().getMobilePhone())
                        .isDeleted(closet.getUser().getIsDeleted())
                        .build())
                .isDeleted(closet.getIsDeleted())
                .build();
    }
}
