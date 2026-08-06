package com.fdb.api.dto.orderitem;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemUpdateRequest {
	
	private Integer quantity;

    private BigDecimal itemPrice;
    
    private Boolean active;

}
