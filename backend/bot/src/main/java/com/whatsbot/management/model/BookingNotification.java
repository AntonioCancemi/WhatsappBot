package com.whatsbot.management.model;

import com.whatsbot.auth.model.Tenant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "booking_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingNotification {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    private Tenant tenant;

    @Column(nullable = false)
    private String message;

    @CreationTimestamp
    private Instant createdAt;
}
