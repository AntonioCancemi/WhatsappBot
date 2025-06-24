package com.whatsbot.broadcast.repository;

import com.whatsbot.broadcast.model.BroadcastMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BroadcastMessageRepository extends JpaRepository<BroadcastMessage, UUID> {
}
