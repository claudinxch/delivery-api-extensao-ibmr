package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String category;
    private String phoneNumber;
    private String address;

    @Column(precision = 10, scale = 2)
    private BigDecimal delivery_fee;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(nullable = true)
    private Boolean active;

    public void deactivate() {
        this.active = false;
    }
}
