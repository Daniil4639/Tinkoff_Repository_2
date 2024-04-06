package edu.java.domain.jpa.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "CHAT_LINK_CONNECTION")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ConnectionEntity {

    @EmbeddedId
    private ConnectionIds id;
}
