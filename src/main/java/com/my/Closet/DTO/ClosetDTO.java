package com.my.Closet.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ClosetDTO {

    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "ClosetType is required")
    private String closetType;

    @NotNull(message = "Closet must have an user")
    private UserDTO user;

    private Boolean deleted;

    private List<JerseyDTO> jerseys;

}
