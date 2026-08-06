package com.fdb.api.dto.menuitem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fdb.api.entity.Restaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuItemResponse {
	
	    private Long id;

	    private String itemName;

	    private String description;

	    private String category;

	    private BigDecimal price;

	    private Boolean available;
	    
	    private LocalDateTime createdAt;

		private LocalDateTime updatedAt;
	    
	    private Restaurant restaurant;

}
