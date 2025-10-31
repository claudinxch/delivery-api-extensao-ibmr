package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant create(Restaurant restaurant) {
        if (restaurantRepository.existsByNameIgnoreCase(restaurant.getName())){
            throw new IllegalArgumentException("Restaurant name already exists");
        }

        if (restaurantRepository.existsByPhoneNumber(restaurant.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        validateRestaurantData(restaurant);

        if (restaurant.getRating() == null) {
            restaurant.setRating(BigDecimal.ZERO);
        }

        if (restaurant.getDelivery_fee() == null) {
            restaurant.setDelivery_fee(BigDecimal.ZERO);
        }

        restaurant.setActive(true);
        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public Optional<Restaurant> findById(UUID id){
        return restaurantRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Restaurant> findByName(String name) {
        return restaurantRepository.findByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category must be provided");
        }
        return restaurantRepository.findByCategoryIgnoreCase(category);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listByCategory(String category, boolean orderByDesc) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category must be provided");
        }
        return restaurantRepository.findByCategoryAndActiveTrueOrderByRatingDesc(category);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listActive() {
        return restaurantRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listActive(boolean orderByDesc) {
        return restaurantRepository.findByActiveTrueOrderByRatingDesc();
    }

    public Restaurant update(UUID id, Restaurant updatedRestaurant) {
        Restaurant restaurant = findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("Restaurant not found: " + id)));

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setAddress(updatedRestaurant.getAddress());
        restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        restaurant.setCategory(updatedRestaurant.getCategory());
        restaurant.setDelivery_fee(updatedRestaurant.getDelivery_fee());

        return restaurantRepository.save(restaurant);
    }

    public void deactivate(UUID id) {
        Restaurant restaurant = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + id));

        if (!restaurant.getActive()) {
            throw new IllegalStateException("Restaurant is already deactivated");
        }

        restaurant.deactivate();
        restaurantRepository.save(restaurant);
    }

    public void activate(UUID id) {
        Restaurant restaurant = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + id));

        if (restaurant.getActive()) {
            throw new IllegalStateException("Restaurant is already activated");
        }

        restaurant.activate();
        restaurantRepository.save(restaurant);
    }

    public boolean isRestaurantActive(UUID id) {
        return restaurantRepository.existsByIdAndActiveTrue(id);
    }

    private void validateRestaurantData(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory.");
        }

        if (restaurant.getAddress() == null || restaurant.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is mandatory");
        }
    }
}
