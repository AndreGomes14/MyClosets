package com.my.Closet.Service;

import com.my.Closet.DTO.WishClosetDTO;
import com.my.Closet.DTO.WishClosetJerseyDTO;
import com.my.Closet.DTO.WishJerseyDTO;
import com.my.Closet.exception.JerseyNotFoundException;
import com.my.Closet.exception.WishClosetCreationException;
import com.my.Closet.exception.WishClosetNotFoundException;
import com.my.Closet.model.WishCloset;
import com.my.Closet.model.WishJersey;
import com.my.Closet.repository.WishClosetRepository;
import com.my.Closet.repository.WishJerseyRepository;
import com.my.Closet.service.WishClosetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class WishClosetServiceTest {

    @Mock
    private WishClosetRepository wishClosetRepository;

    @Mock
    private WishJerseyRepository wishJerseyRepository;

    @InjectMocks
    private WishClosetService wishClosetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
