package com.whatsbot.auth.repository;

import com.whatsbot.auth.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    Optional<AuthUser> findByUsernameAndTenant_Name(String username, String tenantName);

    Optional<AuthUser> findByUsernameAndTenant_Id(String username, UUID tenantId);
}
