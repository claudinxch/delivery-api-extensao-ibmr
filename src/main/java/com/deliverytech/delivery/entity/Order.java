package com.deliverytech.delivery.entity;

import com.deliverytech.delivery.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20)
    private String orderNumber;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;

    private String observation;
    private List<Product> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void addProduct(Product product) {
        this.items.add(product);
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void calculatePrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : items){
            total = total.add(p.getPrice());
        }

        this.totalPrice = total;
    }
}
