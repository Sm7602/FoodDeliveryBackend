package com.fdb.api.dto.restaurant;

import java.time.LocalDateTime;
import java.util.List;

import com.fdb.api.entity.FoodOrder;
import com.fdb.api.entity.MenuItem;
import com.fdb.api.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponse {
	
	    private Long id;

	    private String restaurantName;

	    private String ownerName;

	    private String address;

	    private String phoneNumber;

	    private Double rating;

	    private LocalDateTime createdAt;

		private LocalDateTime updatedAt;

		private Boolean active;

	    private List<MenuItem> menuItems;

	    private List<FoodOrder> orders;
	    
	    private User user;

}
