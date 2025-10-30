package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByRestaurant(Restaurant restaurant);

    boolean existsByNameIgnoreCase(String name);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByAvailableTrue();
}
