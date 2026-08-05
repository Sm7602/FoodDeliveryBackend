package com.fdb.api.dto.deliverypartner;

import java.time.LocalDateTime;
import java.util.List;

import com.fdb.api.entity.FoodOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryPartnerResponse {
	
	    private Long id;

	    private String firstName;

	    private String lastName;

	    private String phoneNumber;

	    private String vehicleNumber;
	    
	    private String drivingLicenseNumber;
	    
	    private String vehicleType;

	    private Boolean available;
	    
	    private LocalDateTime createdAt;

		private LocalDateTime updatedAt;
	    
	    private List<FoodOrder> orders;
	   
	    private long userId;

}
