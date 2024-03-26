package com.my.Closet.repository;

import com.my.Closet.entity.Jersey;
import com.my.Closet.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, UUID> {
    List<Picture> findAllByJerseyId(UUID jerseyId);
}
