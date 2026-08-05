package com.fdb.api.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequest {

	    @NotNull
	    private Long customerId;
	    
	    @NotNull
	    private Long menuItemId;
}
