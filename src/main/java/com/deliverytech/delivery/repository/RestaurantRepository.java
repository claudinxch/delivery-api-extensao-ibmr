package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Optional<Restaurant> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Restaurant> findByCategoryIgnoreCase(String category);

    List<Restaurant> findByActiveTrue();

    List<Restaurant> findByActiveTrueOrderByRatingDesc();

    List<Restaurant> findByCategoryAndActiveTrueOrderByRatingDesc(String category);
}
