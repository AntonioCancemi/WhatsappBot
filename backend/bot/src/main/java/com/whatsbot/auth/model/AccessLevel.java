package com.whatsbot.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "access_levels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLevel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
}
