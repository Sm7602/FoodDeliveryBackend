package com.fdb.api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal itemPrice;

    private BigDecimal subtotal;
    
    private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Boolean active;
    
    @ManyToOne
    private FoodOrder foodOrder;

    @ManyToOne
    private MenuItem menuItem;
}
