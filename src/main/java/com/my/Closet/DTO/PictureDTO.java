package com.my.Closet.DTO;

import com.my.Closet.model.Jersey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDTO {

    private UUID id;
    private byte[] content;
    private boolean isPrimary;
    private Jersey jersey;
}
