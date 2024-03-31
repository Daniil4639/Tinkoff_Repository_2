package edu.java.domain.jpa.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ConnectionIds {
    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private ChatEntity chat;

    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "id")
    private LinkEntity link;
}
