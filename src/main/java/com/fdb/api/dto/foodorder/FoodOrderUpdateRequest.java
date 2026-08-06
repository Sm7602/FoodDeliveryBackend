package com.fdb.api.dto.foodorder;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FoodOrderUpdateRequest {

	    private BigDecimal totalAmount;

	    private String orderStatus;

	    private String deliveryAddress;

}
