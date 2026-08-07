package com.fdb.api.dto.restaurant;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantRequest {
	
	@NotBlank(message = "Restaurant Name is required.")
    private String restaurantName;

    @NotBlank(message = "Owner Name is required.")
    @Pattern(
            regexp = "^[A-Za-z ]{2,50}$",
            message = "Owner Name must contain only letters and be 2 to 50 characters long."
    )
    private String ownerName;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Phone Number is required.")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone Number must be a valid 10-digit Indian mobile number."
    )
    private String phoneNumber;

    @DecimalMin(value = "0.0", message = "Rating cannot be less than 0.")
    @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5.")
    private Double rating;
    
    @NotNull(message = "User Id is required.")
    private Long userId;

}
