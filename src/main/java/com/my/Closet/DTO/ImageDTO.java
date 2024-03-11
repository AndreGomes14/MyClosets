package com.my.Closet.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    private UUID id;
    private String name;
    private Boolean isDeleted;

    private byte[] data;
}
