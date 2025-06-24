package com.whatsbot.modules.auth.repository;

import com.whatsbot.modules.auth.model.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccessLevelRepository extends JpaRepository<AccessLevel, UUID> {
}
