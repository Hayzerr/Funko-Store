package com.bolashak.onlinestorebackend.entities;

import com.bolashak.onlinestorebackend.config.LocalDateTimeAttributeConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.bolashak.onlinestorebackend.constants.ValueConstants.ZONE_ID;

@EqualsAndHashCode
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    T id;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private boolean isActive = true;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = this.createdAt = LocalDateTime.now(ZONE_ID);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZONE_ID);
    }
}
