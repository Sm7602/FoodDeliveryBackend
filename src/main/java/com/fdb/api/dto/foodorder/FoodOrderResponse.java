package com.fdb.api.dto.foodorder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fdb.api.entity.Customer;
import com.fdb.api.entity.DeliveryPartner;
import com.fdb.api.entity.OrderItem;
import com.fdb.api.entity.Restaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class FoodOrderResponse {
	
	    private Long id;

	    private String orderNumber;

	    private BigDecimal totalAmount;

	    private String orderStatus;

	    private String deliveryAddress;

	    private LocalDateTime orderTime;
	    
	    private LocalDateTime updatedAt;
	    
	    private Customer customer;

	    private Restaurant restaurant;

	    private DeliveryPartner deliveryPartner;

	    private List<OrderItem> orderItems;

}
