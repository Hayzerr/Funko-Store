package com.bolashak.onlinestorebackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image extends AbstractEntity<Long> {

    @Column(name = "image_url", nullable = false, length = 1000)
    @NotNull(message = "Image URL is required")
    private String imageUrl;
}