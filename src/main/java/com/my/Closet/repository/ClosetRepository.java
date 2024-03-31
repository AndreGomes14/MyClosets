package com.my.Closet.repository;

import com.my.Closet.model.Closet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClosetRepository extends JpaRepository<Closet, UUID> {
    List<Closet> findAllByDeletedIsFalse();
    List<Closet> findByUserIdAndDeletedIsFalse(UUID userId);
}
