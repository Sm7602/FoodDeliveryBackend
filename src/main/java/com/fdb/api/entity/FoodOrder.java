package com.fdb.api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private BigDecimal totalAmount;

    private String orderStatus;

    private String deliveryAddress;

    private LocalDateTime orderTime;
    
    private LocalDateTime updatedAt;
    
    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private DeliveryPartner deliveryPartner;

    @OneToMany(mappedBy = "foodOrder")
    private List<OrderItem> orderItems;
}