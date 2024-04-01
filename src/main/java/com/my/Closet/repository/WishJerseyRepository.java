package com.my.Closet.repository;

import com.my.Closet.model.WishJersey;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishJerseyRepository {
    WishJersey save(WishJersey wishJersey);

    List<WishJersey> findAll();

    Optional<WishJersey> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
