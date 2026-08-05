package com.fdb.api.dto.customer;

import java.time.LocalDateTime;
import java.util.List;

import com.fdb.api.entity.Cart;
import com.fdb.api.entity.FoodOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
	   
	    private Long id;

	    private String firstName;

	    private String lastName;

	    private String phoneNumber;

	    private String address;

	    private LocalDateTime createdAt;

		private LocalDateTime updatedAt;

		private Boolean active;
	    
	    private List<FoodOrder> orders;

	    private Cart cart;
	    
	    private long userId;
}
