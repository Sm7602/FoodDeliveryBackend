package com.fdb.api.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partnerName;

    private String phoneNumber;

    private String vehicleNumber;

    private Boolean available;
    
    private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "deliveryPartner")
    private List<FoodOrder> orders;
}
