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
public class RegisterRequest {
	
	@NotBlank(message = "firstName Must not be blank")
	private String firstName;
	
	@NotBlank(message = "lastName Must not be blank")
	private String lastName;
	
	@NotBlank(message = "email Must not be blank")
	private String email;
	
	@NotBlank(message = "password Must not be blank")
	private String password;
	
}
