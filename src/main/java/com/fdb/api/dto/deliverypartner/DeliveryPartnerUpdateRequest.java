package com.fdb.api.dto.deliverypartner;

import lombok.Data;

@Data
public class DeliveryPartnerUpdateRequest {
	
	    private String firstName;

	    private String lastName;

	    private String phoneNumber;

	    private String vehicleNumber;
	    
	    private String drivingLicenseNumber;
	    
	    private String vehicleType;
	    
	    private Boolean available;

}
