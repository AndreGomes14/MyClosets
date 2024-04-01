package com.my.Closet.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class WishClosetJerseyDTO {
    WishClosetDTO wishClosetDTO;
    JerseyDTO jerseyDTO;
}
