package com.whatsbot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Configuration for a tenant using the WhatsApp bot.
 */
@Entity
@Table(name = "tenant_configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantConfig {

    @Id
    @GeneratedValue
    private UUID id;

    /** Unique identifier for the tenant */
    @Column(nullable = false, unique = true)
    private String tenantId;

    @Column(nullable = false)
    private String baseUrl;

    @Column(nullable = false)
    private String phoneNumberId;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String appSecret;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
