package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant create(Restaurant restaurant) {
        if(restaurantRepository.existsByNameIgnoreCase(restaurant.getName())){
            throw new IllegalArgumentException("Restaurant name already exists");
        }

        validateRestaurantData(restaurant);

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
        return restaurantRepository.findByCategoryIgnoreCase(category);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> listByCategory(String category, boolean orderByDesc) {
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

    private void validateRestaurantData(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory.");
        }

        if (restaurant.getAddress() == null || restaurant.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is mandatory");
        }
    }
}
