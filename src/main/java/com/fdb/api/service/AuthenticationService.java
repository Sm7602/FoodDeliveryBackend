package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdb.api.dao.CustomerRepository;
import com.fdb.api.dao.DeliveryPartnerRepository;
import com.fdb.api.dao.RestaurantRepository;
import com.fdb.api.dao.UserRepository;
import com.fdb.api.dto.AuthenticationRequest;
import com.fdb.api.dto.AuthenticationResponse;
import com.fdb.api.dto.CustomerRegisterRequest;
import com.fdb.api.dto.DeliveryPartnerRegisterRequest;
import com.fdb.api.dto.RestaurantRegisterRequest;
import com.fdb.api.entity.Customer;
import com.fdb.api.entity.DeliveryPartner;
import com.fdb.api.entity.Restaurant;
import com.fdb.api.entity.Role;
import com.fdb.api.entity.User;
import com.fdb.api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	
	private final CustomerRepository customerRepository;
	
	private final DeliveryPartnerRepository deliveryPartnerRepository;
	
	private final RestaurantRepository restaurantRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse registerCustomer(CustomerRegisterRequest request) {

		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		
	    User user = User.builder()
	            .firstname(request.getFirstName())
	            .lastname(request.getLastName())
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.CUSTOMER)
	            .build();

	    user = userRepository.save(user);

	    Customer customer = Customer.builder()
	    		.firstName(request.getFirstName())
	            .lastName(request.getLastName())
                .phoneNumber(request.getPhone())
                .address(request.getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .user(user)
                .build();

        customerRepository.save(customer);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
	            .token(jwtToken)
	            .build();
	}
	
	public AuthenticationResponse registerRestaurant(RestaurantRegisterRequest request) {

		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		
	    User user = User.builder()
	            .firstname(request.getOwnerName())
	            .lastname("")
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.RESTURANT)
	            .build();

	    user = userRepository.save(user);

	    Restaurant restaurant = Restaurant.builder()
	            .restaurantName(request.getRestaurantName())
	            .ownerName(request.getOwnerName())
	            .phoneNumber(request.getPhone())
                .address(request.getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .user(user)
	            .build();

	    restaurantRepository.save(restaurant);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
	            .token(jwtToken)
	            .build();
	}
	
	
	public AuthenticationResponse registerDeliveryPartner(DeliveryPartnerRegisterRequest request) {

		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		
	    User user = User.builder()
	            .firstname(request.getFirstName())
	            .lastname(request.getLastName())
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.DELIVERYPARTNER)
	            .build();

	    user = userRepository.save(user);

	    DeliveryPartner deliveryPartner = DeliveryPartner.builder()
	    		.firstName(request.getFirstName())
	            .lastName(request.getLastName())
                .phoneNumber(request.getPhone())
                .vehicleNumber(request.getVehicleNumber())
                .drivingLicenseNumber(request.getDrivingLicenseNumber())
                .vehicleType(request.getVehicleType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .available(true)
                .user(user)
	            .build();

	    deliveryPartnerRepository.save(deliveryPartner);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
	            .token(jwtToken)
	            .build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail()
						,request.getPassword()));
		
		var user =userRepository.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken =jwtService.generaTetoken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.email(user.getEmail())
                .role(user.getRole().name())
				.build();
	}

}

