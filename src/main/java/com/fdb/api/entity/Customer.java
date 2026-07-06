package com.fdb.api.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Boolean active;
    
    @OneToMany(mappedBy = "customer")
    private List<FoodOrder> orders;

    @OneToOne(mappedBy = "customer")
    private Cart cart;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
