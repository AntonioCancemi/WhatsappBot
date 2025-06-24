package com.whatsbot.modules.booking.model;

import com.whatsbot.modules.messaging.model.Contact;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "conversation_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationState {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(name = "flow_id", nullable = false)
    private String flowId;

    @Column(nullable = false)
    private String step;

    @Lob
    private String data;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
