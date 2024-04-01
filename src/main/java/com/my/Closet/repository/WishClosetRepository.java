package com.my.Closet.repository;

import com.my.Closet.model.WishCloset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishClosetRepository extends JpaRepository<WishCloset, UUID> {
    List<WishCloset> findByUserId(UUID userId);
}
