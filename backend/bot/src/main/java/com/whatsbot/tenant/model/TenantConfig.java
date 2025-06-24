package com.whatsbot.tenant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * Tenant configuration data.
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

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String phoneNumber;

    private String phoneNumberId;

    private String accessToken;

    @Column(nullable = false)
    private String appSecret;

    @CreationTimestamp
    private Instant createdAt;
}
