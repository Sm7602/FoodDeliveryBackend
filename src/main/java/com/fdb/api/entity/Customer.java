package com.fdb.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String email;

    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Boolean active;
    
    @OneToMany(mappedBy = "customer")
    private List<FoodOrder> orders;

    @OneToOne(mappedBy = "customer")
    private Cart cart;
}
