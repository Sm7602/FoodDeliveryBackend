package com.fdb.api.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRequest {

	    @NotBlank(message = "First Name is required.")
	    private String firstName;

	    @NotBlank(message = "Last Name is required.")
	    private String lastName;

	    @NotBlank(message = "Phone Number is required.")
	    @Pattern(regexp = "^[0-9]{10}$",
	            message = "Phone Number must contain 10 digits.")
	    private String phoneNumber;

	    @NotBlank(message = "Address  is required.")
	    private String address;
	    
	    @NotNull(message = "User Id is required.")
	    private Long userId;
}
