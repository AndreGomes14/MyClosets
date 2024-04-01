package com.my.Closet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Slf4j
public class WishCloset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    final String name = "Wish Closet";

    private Boolean deleted;

    @OneToMany(mappedBy = "wishCloset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishJersey> wishJerseys = new ArrayList<>();

    @OneToOne(mappedBy = "wishClosetOwner", cascade = CascadeType.ALL)
    private User user;
}
