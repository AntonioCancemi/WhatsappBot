package com.whatsbot.repository;

import com.whatsbot.model.ConversationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationStateRepository extends JpaRepository<ConversationState, UUID> {
    Optional<ConversationState> findByUser_IdAndFlowId(UUID userId, String flowId);
}
