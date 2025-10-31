package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByRestaurant(Restaurant restaurant);

    boolean existsByNameIgnoreCaseAndRestaurant(String name, Restaurant restaurant);

    List<Product> findByAvailableTrueAndCategoryIgnoreCase(String category);

    List<Product> findByAvailableTrue();

    List<Product> findByAvailableTrueAndRestaurant(Restaurant restaurant);
}
