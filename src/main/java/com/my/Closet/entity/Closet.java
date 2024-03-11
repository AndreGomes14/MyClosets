package com.my.Closet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Slf4j
public class Closet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "ClosetType is required")
    private String closetType;

    private String isDeleted;

    @ManyToOne
    private List<Jersey> jerseys = new ArrayList<>();

    @ManyToOne
    private List<User> users = new ArrayList<>();
}
