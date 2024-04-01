package com.my.Closet.DTO;

import com.my.Closet.model.Picture;
import com.my.Closet.model.WishCloset;
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
public class WishJerseyDTO {

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

    private WishCloset wishCloset;

    private List<Picture> pictures;
}
