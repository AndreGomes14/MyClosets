package com.my.Closet.entity;

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
public class Jersey {

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
    private List<String> patches;
    private String condition;
    private String category;
    private String acquisitionDate;
    private String buyPrice;
    private String sellPrice;
    private Boolean deleted;

    @ManyToOne
    private Closet closet;

    @OneToMany
    private List<Picture> picture;

    public Jersey(UUID uuid, String s, String s1) {
    }
}
