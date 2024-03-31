package edu.java.domain.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "CHATS")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatEntity {

    @Id
    @Column(name = "chat_id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Timestamp creationDate;

    @Column(name = "wait_track")
    private Integer waitTrack;

    @Column(name = "wait_untrack")
    private Integer waitUntrack;
}
