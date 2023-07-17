package com.twit.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twit.apiResponse.ApiResponseEntity;
import com.twit.apiResponse.Code;
import com.twit.apiResponse.auth.LoginRes;
import com.twit.apiResponse.auth.RegisterRes;
import com.twit.configuration.JwtService;
import com.twit.entity.User;
import com.twit.entity.User.Provider;
import com.twit.entity.User.Role;
import com.twit.oauth2.CustomOAuth2User;
import com.twit.repository.UserRepository;
import com.twit.reqs.AuthenticationRequest;
import com.twit.reqs.RegisterRequest;
import com.twit.response.InvalidCombination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public ApiResponseEntity register(RegisterRequest req) {
		var user = User.builder()
						.firstName(req.getFirstName())
						.lastName(req.getLastName())
						.email(req.getEmail())
						.password(passwordEncoder.encode(req.getPassword()))
						.role(Role.ROLE_USER)
						.oauthId(null)
						.provider(Provider.LOCAL)
						.build();
		
		userRepository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		return ApiResponseEntity.builder()
								.code(Code.SUCCESS)
								.status(HttpStatus.CREATED)
								.result(RegisterRes.builder()
													.email(user.getEmail())
													.token(jwtToken)
													.build())
								.build();
	}
	

	
	public ApiResponseEntity authenticate(AuthenticationRequest request) throws Exception {
		if(checkUser(request.getEmail(), request.getPassword())){
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
					);
			var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
			var jwtToken = jwtService.generateToken(user);
			
			return ApiResponseEntity.builder()
									.code(Code.SUCCESS)
									.status(HttpStatus.OK)
									.result(LoginRes.builder()
													.email(user.getEmail())
													.token(jwtToken)
													.build())
									.build();
		}{
			throw new InvalidCombination("Incorrect Email and password Combination");
		}

			
	}
	
	public boolean checkUser(String email, String password){
		if(userRepository.findByEmail(email).isEmpty()) {
			return false;
		}
		if(passwordEncoder.matches( password,userRepository.findByEmail(email).get().getPassword())) {
			return true;
		}
		return false;
	}
	
	public void processOAuthPostLogin(CustomOAuth2User oAuth2User) {
		log.info("THis is the data in processOAuthPostLogin : {}",oAuth2User);
//		User existUser = userRepository.findByEmail(oAuth2User.getEmail()).orElse(null);
//		 if (existUser == null) {
//			 var user = User.builder()
//						.firstName(oAuth2User.getFirstName())
//						.lastName(req.getLastName())
//						.email(req.getEmail())
//						.password(passwordEncoder.encode(req.getPassword()))
//						.role(Role.ROLE_USER)
//						.build();       
//	        }
	}
	
}
