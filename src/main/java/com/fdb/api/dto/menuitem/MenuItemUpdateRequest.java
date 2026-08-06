package com.fdb.api.dto.menuitem;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MenuItemUpdateRequest {

	
	    private String itemName;

	    private String description;

	    private String category;

	    private BigDecimal price;

	    private Boolean available;
}
