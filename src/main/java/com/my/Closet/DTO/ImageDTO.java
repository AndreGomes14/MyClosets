package com.my.Closet.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Data
@Builder
@Slf4j
public class ImageDTO {

    private UUID id;
    private String name;
    private byte[] data;
}
