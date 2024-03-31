package com.my.Closet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "users") // Specify a custom table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "fullname is required")
    private String fullName;

    private String username;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "mobile phone is required")
    private String mobilePhone;

    private Boolean deleted;
    @OneToMany
    private List<Closet> closets;

}
