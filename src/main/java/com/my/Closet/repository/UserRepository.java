package com.my.Closet.repository;

import com.my.Closet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByDeletedFalse();
    Optional<User> findByIdAndDeletedFalse(UUID id);
}
