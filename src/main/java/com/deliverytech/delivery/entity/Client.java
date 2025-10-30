package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data // This already includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private Boolean active;

    public void deactivate() {
        this.active = false;
    }
}

