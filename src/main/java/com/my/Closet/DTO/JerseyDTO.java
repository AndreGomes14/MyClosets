package com.my.Closet.DTO;

import com.my.Closet.entity.Closet;
import com.my.Closet.entity.Image;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Slf4j
public class JerseyDTO {
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
    private String isDeleted;
    private List<Image> photos;

    private List<Closet> closets;
}
