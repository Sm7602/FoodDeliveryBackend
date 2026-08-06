package com.fdb.api.dto.menuitem;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MenuItemRequest {

	
	    @NotBlank(message = "Item Name is required.")
	    private String itemName;

	    @NotBlank(message = "Description is required.")
	    private String description;

	    @NotBlank(message = "Category is required.")
	    @Pattern(
	            regexp = "^(Starter|Main Course|Dessert|Beverage|Snack|Combo)$",
	            message = "Category must be Starter, Main Course, Dessert, Beverage, Snack, or Combo."
	    )
	    private String category;

	    @NotNull(message = "Price is required.")
	    @DecimalMin(value = "1.00", message = "Price must be greater than 0.")
	    @Digits(integer = 8, fraction = 2,
	            message = "Price can have up to 8 digits and 2 decimal places.")
	    private BigDecimal price;

	    @NotNull(message = "Restaurant Id is required.")
	    @Positive(message = "Restaurant Id must be greater than 0.")
	    private Long restaurantId;
}
