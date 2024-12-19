package com.bolashak.onlinestorebackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_items", // Name of the join table
            joinColumns = @JoinColumn(name = "wishlist_id"), // FK to Wishlist
            inverseJoinColumns = @JoinColumn(name = "product_id") // FK to Product
    )
    private List<Product> wishlistItems;
}
