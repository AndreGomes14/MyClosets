package com.my.Closet.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Slf4j
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Lob
    private byte[] content;

    private boolean isPrimary;

    @ManyToOne
    private Jersey jersey;

    @ManyToOne
    private WishJersey wishJersey;
}
