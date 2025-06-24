package com.whatsbot.modules.messaging.repository;

import com.whatsbot.modules.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByContact_IdOrderByTimestampAsc(UUID userId);
    Optional<Message> findFirstByContact_IdOrderByTimestampDesc(UUID userId);
}
