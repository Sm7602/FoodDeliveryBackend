package com.fdb.api.service;

import java.util.HashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.UserRepository;
import com.fdb.api.dto.AuthenticationRequest;
import com.fdb.api.dto.AuthenticationResponse;
import com.fdb.api.dto.RegisterRequest;
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

	private final UserRepository repo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public  AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
		
		repo.save(user);
		
		var jwtToken =jwtService.generateToken(new HashMap<>(), user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail()
						,request.getPassword()));
		
		var user =repo.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken =jwtService.generaTetoken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.email(user.getEmail())
                .role(user.getRole().name())
				.build();
	}

}

