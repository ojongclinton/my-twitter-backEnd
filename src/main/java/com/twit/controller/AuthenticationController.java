package com.twit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.twit.apiResponse.ApiResponseEntity;
import com.twit.reqs.AuthenticationRequest;
import com.twit.reqs.RegisterRequest;
import com.twit.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponseEntity> register(
			@RequestBody @Valid RegisterRequest request
			){
		return ResponseEntity.ok(authService.register(request));
		
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<ApiResponseEntity> authenticate(
			@RequestBody @Valid AuthenticationRequest request
			) throws Exception{
		log.info("User is here now");
		return ResponseEntity.ok(authService.authenticate(request));
		
	}
}
