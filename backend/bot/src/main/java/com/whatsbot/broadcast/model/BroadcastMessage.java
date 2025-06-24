package com.whatsbot.broadcast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "broadcast_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastMessage {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID tenantId;

    @Column(nullable = false)
    private String text;

    private Instant scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BroadcastStatus status;

    @CreationTimestamp
    private Instant createdAt;
}
