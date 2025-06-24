package com.whatsbot.user.repository;

import com.whatsbot.user.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByUser_IdOrderByTimestampAsc(UUID userId);
    Optional<Message> findFirstByUser_IdOrderByTimestampDesc(UUID userId);
}
