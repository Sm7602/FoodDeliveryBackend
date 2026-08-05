package com.fdb.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private String address;
}
