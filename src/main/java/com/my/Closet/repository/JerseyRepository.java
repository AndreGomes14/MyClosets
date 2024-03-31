package com.my.Closet.repository;

import com.my.Closet.model.Jersey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, UUID> {
    List<Jersey> findAllByDeletedIsFalse();
}
