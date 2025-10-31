package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.entity.Order;
import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.enums.OrderStatus;
import com.deliverytech.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    RestaurantService restaurantService;
    ProductService productService;

    public Order register(Order order) {
        if(orderRepository.existsByOrderNumberIgnoreCase(order.getOrderNumber()))
            throw new IllegalArgumentException("Order number already exists");

        validateOrder(order);

        return orderRepository.save(order);
    }

    public Order udpateStatus(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus().equals(OrderStatus.DELIVERED))
            throw new IllegalArgumentException("Order has been delivered");

        validateStatusUpdate(order, status);

        order.updateStatus(status);

        return orderRepository.save(order);
    }

    public List<Order> listByClient(Client client) {
        return orderRepository.findByClient(client);
    }

    public List<Order> listByClient(Client client, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByClientAndCreatedAtBetween(client, startDate, endDate);
    }

    public List<Order> listByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> listByClientAndStatus(Client client, OrderStatus status) {
        return orderRepository.findByClientAndStatus(client, status);
    }

    public List<Order> listByCreatedAt(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    private void validateStatusUpdate(Order order, OrderStatus newStatus) {
        if(order.getStatus().equals(newStatus)) throw new IllegalArgumentException("Status is the same");

        if(order.getStatus().equals(OrderStatus.PENDING) && !newStatus.equals(OrderStatus.CONFIRMED))
            throw new IllegalArgumentException("Invalid status");

        if(order.getStatus().equals(OrderStatus.CONFIRMED) && !newStatus.equals(OrderStatus.DELIVERED))
            throw new IllegalArgumentException("Invalid status");
    }

    private void validateOrder(Order order) {
        if(order.getOrderNumber() == null || order.getOrderNumber().trim().isEmpty())
            throw new IllegalArgumentException("Order number is required");

        if(order.getClient() == null)
            throw new IllegalArgumentException("Order needs a client");

        if(order.getRestaurant() == null)
            throw new IllegalArgumentException("Order needs a restaurant");

        if(order.getItems().isEmpty())
            throw new IllegalArgumentException("Order is empty!");
    }
}
