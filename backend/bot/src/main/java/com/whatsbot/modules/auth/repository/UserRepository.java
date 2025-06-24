package com.whatsbot.modules.auth.repository;

import com.whatsbot.modules.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndTenant_Name(String email, String tenantName);

    Optional<User> findByEmailAndTenant_Id(String email, UUID tenantId);
}
