package com.my.Closet.DTO;

import com.my.Closet.entity.Closet;
import jakarta.validation.constraints.NotBlank;
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
public class UserDTO {

    private UUID id;
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private  String email;

    @NotBlank(message = "mobile phone is required")
    private String mobilePhone;
    private Boolean isDeleted;


    private Closet closet;
}
