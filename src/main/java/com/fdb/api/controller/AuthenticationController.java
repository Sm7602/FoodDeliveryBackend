package com.fdb.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.dto.AuthenticationRequest;
import com.fdb.api.dto.AuthenticationResponse;
import com.fdb.api.dto.CustomerRegisterRequest;
import com.fdb.api.dto.DeliveryPartnerRegisterRequest;
import com.fdb.api.dto.RestaurantRegisterRequest;
import com.fdb.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	@PostMapping("/registerCustomer")
	public ResponseEntity<AuthenticationResponse> registerCustomer(
			@RequestBody CustomerRegisterRequest request){
		System.out.println("AuthgenticationController");
		return ResponseEntity.ok(service.registerCustomer(request));
	}
	
	@PostMapping("/registerRestaurant")
	public ResponseEntity<AuthenticationResponse> registerRestaurant(
			@RequestBody RestaurantRegisterRequest request){
		System.out.println("AuthgenticationController");
		return ResponseEntity.ok(service.registerRestaurant(request));
	}
	
	@PostMapping("/registerDeliveryPartner")
	public ResponseEntity<AuthenticationResponse> registerDeliveryPartner(
			@RequestBody DeliveryPartnerRegisterRequest request){
		System.out.println("AuthgenticationController");
		return ResponseEntity.ok(service.registerDeliveryPartner(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request){
		System.out.println("AuthgenticationController login.....");
		return ResponseEntity.ok(service.authenticate(request));
	}
}

