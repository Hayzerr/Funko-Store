package com.bolashak.onlinestorebackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @Column(name = "token_id", nullable = false, updatable = false)
    private String tokenId;

    @Column(nullable = false, unique = true)
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        if (this.tokenId == null) {
            this.tokenId = UUID.randomUUID().toString(); // Generate unique ID before saving
        }
    }
}

