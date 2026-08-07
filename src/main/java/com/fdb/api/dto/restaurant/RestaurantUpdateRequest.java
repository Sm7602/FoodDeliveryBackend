package com.fdb.api.dto.restaurant;

import lombok.Data;

@Data
public class RestaurantUpdateRequest {
	
	private String restaurantName;

    private String ownerName;

    private String address;

    private String phoneNumber;

    private Double rating;
    
    private Boolean active;

}
