package com.my.Closet.DTO;

import com.my.Closet.entity.Closet;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@Slf4j
public class UserDTO {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private  String email;

    @NotBlank(message = "mobile phone is required")
    private String mobilePhone;

    private List<Closet> closets;
}
