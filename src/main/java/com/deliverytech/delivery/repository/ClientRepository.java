package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Client> findByActiveTrue();

    List<Client> findByNameContainingIgnoreCase(String name);
}

