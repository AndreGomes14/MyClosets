package com.my.Closet.model;

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

    final String name = "My Closet";

    private Boolean deleted;

    @OneToMany(mappedBy = "closet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jersey> jerseys = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
