package com.fdb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRegisterRequest {

    private String restaurantName;

    private String ownerName;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String licenseNumber;
}
