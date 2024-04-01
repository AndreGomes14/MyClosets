package com.my.Closet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Slf4j
public class WishJersey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String clubName;
    private String playerName;
    private String number;
    private String season;
    private String competition;
    private String brand;
    private String color;
    private String size;
    private Boolean deleted;

    @ManyToOne
    private WishCloset wishCloset;

    @OneToMany(mappedBy = "WishJersey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures;
}
