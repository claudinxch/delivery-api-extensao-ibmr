package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    RestaurantService restaurantService;

    public Product register(Product product, UUID restaurantId) {
        Optional<Restaurant> restaurant = restaurantService.findById(restaurantId);

        if(restaurant.isEmpty()) throw new IllegalArgumentException("Restaurant not found");

        if(!restaurantService.isRestaurantActive(restaurant.get().getId())) throw new IllegalArgumentException("Restaurant is not active");

        if(productRepository.existsByNameIgnoreCaseAndRestaurant(product.getName(), restaurant.get()))
            throw new IllegalArgumentException("Product with the same name already exists in this restaurant");

        product.setRestaurant(restaurant.get());

        return productRepository.save(product);
    }

    public Product update(UUID id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found."));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());

        validateProductData(product);

        return productRepository.save(product);
    }

    public List<Product> listAllByRestaurant(Restaurant restaurant) {
        return productRepository.findByRestaurant(restaurant);
    }

    public List<Product> listAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    public List<Product> listAvailableProducts(Restaurant restaurant){
        return productRepository.findByAvailableTrueAndRestaurant(restaurant);
    }

    public List<Product> listByCategory(String category) {
        return productRepository.findByAvailableTrueAndCategoryIgnoreCase(category);
    }

    private void validateProductData(Product product) {
        if(product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory");
        }

        if(product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category is mandatory");
        }

        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid price");
        }
    }
}
