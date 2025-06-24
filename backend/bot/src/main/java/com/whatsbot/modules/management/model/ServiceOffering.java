package com.whatsbot.modules.management.model;

import com.whatsbot.modules.auth.model.Tenant;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOffering {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Tenant tenant;

    @Column(nullable = false)
    private String name;

    private int durationMinutes;
    private int bufferMinutes;
    private int maxPerSlot;
}
