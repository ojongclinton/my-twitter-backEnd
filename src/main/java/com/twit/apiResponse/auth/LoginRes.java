package com.twit.apiResponse.auth;

import com.twit.apiResponse.ResultInterface;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRes implements ResultInterface{
	private String email;
	private String token;
}
