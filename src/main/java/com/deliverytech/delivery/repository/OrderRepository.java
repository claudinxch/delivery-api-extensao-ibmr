package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.entity.Order;
import com.deliverytech.delivery.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByClient(Client client);

    boolean existsByOrderNumberIgnoreCase(String orderNumber);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByClientAndStatus(Client client, OrderStatus status);

    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByClientAndCreatedAtBetween(Client client, LocalDateTime startDate, LocalDateTime endDate);
}
