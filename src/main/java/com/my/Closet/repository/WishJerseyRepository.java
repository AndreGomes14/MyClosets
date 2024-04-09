package com.my.Closet.repository;

import com.my.Closet.model.WishCloset;
import com.my.Closet.model.WishJersey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishJerseyRepository extends JpaRepository<WishJersey, UUID> {
    WishJersey save(WishJersey wishJersey);

    List<WishJersey> findAll();

    Optional<WishJersey> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
