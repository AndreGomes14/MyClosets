package com.my.Closet.service;

import com.my.Closet.DTO.ClosetDTO;
import com.my.Closet.DTO.ClosetJerseyDTO;
import com.my.Closet.DTO.JerseyDTO;
import com.my.Closet.DTO.UserDTO;
import com.my.Closet.model.Closet;
import com.my.Closet.model.Jersey;
import com.my.Closet.model.User;
import com.my.Closet.exception.ClosetNotFoundException;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.repository.ClosetRepository;
import com.my.Closet.repository.JerseyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try {
            Closet closet = Closet.builder()
                    .id(UUID.randomUUID()) // Gerar um novo UUID para o closet
                    .user(User.builder()
                            .id(createClosetDTO.getUser().getId())
                            .username(createClosetDTO.getUser().getUsername())
                            .email(createClosetDTO.getUser().getEmail())
                            .mobilePhone(createClosetDTO.getUser().getMobilePhone())
                            .deleted(false) // Por padrão, o usuário não está deletado
                            .build())
                    .deleted(false) // Por padrão, o closet não está deletado
                    .build();
            Closet savedCloset = closetRepository.save(closet);
            return ClosetDTO.builder()
                    .id(savedCloset.getId())
                    .user(UserDTO.builder()
                            .id(savedCloset.getUser().getId())
                            .username(savedCloset.getUser().getUsername())
                            .email(savedCloset.getUser().getEmail())
                            .mobilePhone(savedCloset.getUser().getMobilePhone())
                            .deleted(savedCloset.getUser().getDeleted())
                            .build())
                    .deleted(savedCloset.getDeleted())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while creating closet", e);
            throw e;
        }
    }

    public List<ClosetDTO> getAllClosets() {
        try {
            List<Closet> closets = closetRepository.findAllByDeletedIsFalse();
            List<ClosetDTO> closetDTOs = new ArrayList<>();
            for (Closet closet : closets) {
                closetDTOs.add(convertToDTO(closet));
            }
            return closetDTOs;
        } catch (Exception e) {
            log.error("Error occurred while fetching all closets", e);
            throw e;
        }
    }
    public List<ClosetDTO> getClosetsByUserId(UUID userId) {
        try {
            List<Closet> closets = closetRepository.findByUserIdAndDeletedIsFalse(userId);
            List<ClosetDTO> closetDTOs = new ArrayList<>();
            for (Closet closet : closets) {
                ClosetDTO closetDTO = new ClosetDTO();
                closetDTO.setId(closet.getId());
                // Adicione outros atributos conforme necessário
                closetDTOs.add(closetDTO);
            }
            return closetDTOs;
        } catch (Exception e) {
            log.error("Error occurred while fetching closets by user ID: {}", userId, e);
            throw e;
        }
    }

    public void deleteCloset(UUID closetId) {
        try {
            Optional<Closet> optionalCloset = closetRepository.findById(closetId);
            if (optionalCloset.isPresent()) {
                Closet closet = optionalCloset.get();
                closet.setDeleted(true);
                closetRepository.save(closet);
                log.info("Closet soft deleted with ID: {}", closetId);
            } else {
                log.warn("Closet not found with ID: {}", closetId);
                throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
            }
        } catch (Exception e) {
            log.error("Error occurred while soft deleting closet with ID: {}", closetId, e);
            throw e;
        }
    }

    public void addJerseyToCloset(ClosetJerseyDTO closetJerseyDTO) {
        try {
            Optional<Closet> optionalCloset = closetRepository.findById(closetJerseyDTO.getClosetDTO().getId());
            if (optionalCloset.isPresent()) {
                Closet closet = optionalCloset.get();
                Optional<Jersey> optionalJersey = jerseyRepository.findById(closetJerseyDTO.getJerseyDTO().getId());
                if (optionalJersey.isPresent()) {
                    Jersey jersey = optionalJersey.get();
                    closet.getJerseys().add(jersey);
                    closetRepository.save(closet);
                    log.info("Jersey added to closet. Closet ID: {}, Jersey ID: {}", closetJerseyDTO.getClosetDTO().getId(), closetJerseyDTO.getJerseyDTO().getId());
                } else {
                    log.warn("Jersey not found with ID: {}", closetJerseyDTO.getJerseyDTO().getId());
                    throw new JerseyNotFoundException("Jersey not found with ID: " + closetJerseyDTO.getJerseyDTO().getId());
                }
            } else {
                log.warn("Closet not found with ID: {}", closetJerseyDTO.getClosetDTO().getId());
                throw new ClosetNotFoundException("Closet not found with ID: " + closetJerseyDTO.getClosetDTO().getId());
            }
        } catch (Exception e) {
            log.error("Error occurred while adding jersey to closet. Closet ID: {}, Jersey ID: {}", closetJerseyDTO.getClosetDTO().getId(), closetJerseyDTO.getJerseyDTO().getId(), e);
            throw e;
        }
    }

    public List<JerseyDTO> getAllJerseysByClosetId(UUID closetId) {
        try {
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
                log.warn("Closet not found with ID: {}", closetId);
                throw new ClosetNotFoundException("Closet not found with ID: " + closetId);
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching jerseys by closet ID: {}", closetId, e);
            throw e;
        }
    }

    private ClosetDTO convertToDTO(Closet closet) {
        return ClosetDTO.builder()
                .id(closet.getId())
                .user(UserDTO.builder()
                        .id(closet.getUser().getId())
                        .username(closet.getUser().getUsername())
                        .email(closet.getUser().getEmail())
                        .mobilePhone(closet.getUser().getMobilePhone())
                        .deleted(closet.getUser().getDeleted())
                        .build())
                .deleted(closet.getDeleted())
                .build();
    }
}
