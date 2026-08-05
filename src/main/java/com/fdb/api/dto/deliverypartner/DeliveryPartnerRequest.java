package com.fdb.api.dto.deliverypartner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeliveryPartnerRequest {
	
	    @NotBlank(message = "First Name is required.")
	    @Pattern(
	        regexp = "^[A-Za-z ]{2,50}$",
	        message = "First Name must contain only letters and be 2 to 50 characters long.")
	    private String firstName;

	    @NotBlank(message = "Last Name is required.")
	    @Pattern(
	        regexp = "^[A-Za-z ]{2,50}$",
	        message = "Last Name must contain only letters and be 2 to 50 characters long.")
	    private String lastName;

	    @NotBlank(message = "Phone Number is required.")
	    @Pattern(
	        regexp = "^[6-9][0-9]{9}$",
	        message = "Phone Number must be a valid 10-digit Indian mobile number.")
	    private String phoneNumber;

	    @NotBlank(message = "Vehicle Number is required.")
	    @Pattern(
	        regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$",
	        message = "Vehicle Number must be in a valid format (e.g. WB12AB1234).")
	    private String vehicleNumber;

	    @NotBlank(message = "Driving License Number is required.")
	    @Pattern(
	        regexp = "^[A-Z]{2}[0-9]{13}$",
	        message = "Driving License Number must be valid (e.g. WB0120231234567)." )
	    private String drivingLicenseNumber;

	    @NotBlank(message = "Vehicle Type is required.")
	    @Pattern(
	        regexp = "^(Bike|Scooter|Car|Van|Truck)$",
	        message = "Vehicle Type must be Bike, Scooter, Car, Van, or Truck.")
	    private String vehicleType;

	    @NotNull(message = "User Id is required.")
	    private Long userId;

}
