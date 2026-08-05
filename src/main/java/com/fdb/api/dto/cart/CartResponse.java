package com.fdb.api.dto.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fdb.api.entity.MenuItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class CartResponse {
	
	    private Long id;

	    private BigDecimal totalAmount;

	    private Boolean active;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

	    private Long customerId;
	    
	    private List<MenuItem> menuItems;

}
