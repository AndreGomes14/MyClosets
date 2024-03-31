package com.my.Closet.DTO;

import com.my.Closet.model.Closet;
import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {

    private UUID id;

    private String username;

    @NotBlank(message = "email is required")
    private  String email;

    private String mobilePhone;
    private Boolean deleted;

    private Closet closet;

    public UserDTO(UUID uuid, String testuser, String mail, String number) {
    }
}
