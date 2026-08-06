package com.fdb.api.dto.orderitem;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequest {
	
	@NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "Item Price is required.")
    @DecimalMin(value = "1.00", message = "Item Price must be greater than 0.")
    @Digits(integer = 8, fraction = 2,
            message = "Item Price can have up to 8 digits and 2 decimal places.")
    private BigDecimal itemPrice;

    @NotNull(message = "Food Order Id is required.")
    @Positive(message = "Food Order Id must be greater than 0.")
    private Long foodOrderId;

    @NotNull(message = "Menu Item Id is required.")
    @Positive(message = "Menu Item Id must be greater than 0.")
    private Long menuItemId;

}
