package com.twit.reqs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	
	@NotBlank(message = "email Must not be blank")
	private String email;
	
	@NotBlank(message = "password Must not be blank")
	private String password;
	
}
