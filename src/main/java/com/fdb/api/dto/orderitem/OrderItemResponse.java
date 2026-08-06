package com.fdb.api.dto.orderitem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fdb.api.entity.FoodOrder;
import com.fdb.api.entity.MenuItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
	
	private Long id;

    private Integer quantity;

    private BigDecimal itemPrice;

    private BigDecimal subtotal;
    
    private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Boolean active;

    private FoodOrder foodOrder;

    private MenuItem menuItem;

}
