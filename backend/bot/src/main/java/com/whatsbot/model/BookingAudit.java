package com.whatsbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "booking_audit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingAudit {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private String action;

    @CreationTimestamp
    private Instant timestamp;
}
