package com.whatsbot.modules.booking.repository;

import com.whatsbot.modules.booking.model.ConversationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationStateRepository extends JpaRepository<ConversationState, UUID> {
    Optional<ConversationState> findByContact_IdAndFlowId(UUID userId, String flowId);
}
