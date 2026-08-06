package com.fdb.api.dto.foodorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class FoodOrderRequest {

	    @NotBlank(message = "Delivery Address is required.")
	    private String deliveryAddress;

	    @NotNull(message = "Customer Id is required.")
	    @Positive(message = "Customer Id must be greater than 0.")
	    private Long customerId;

	    @NotNull(message = "Restaurant Id is required.")
	    @Positive(message = "Restaurant Id must be greater than 0.")
	    private Long restaurantId;

	    @NotNull(message = "Delivery Partner Id is required.")
	    @Positive(message = "Delivery Partner Id must be greater than 0.")
	    private Long deliveryPartnerId;}
