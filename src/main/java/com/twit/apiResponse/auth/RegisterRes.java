package com.twit.apiResponse.auth;

import com.twit.apiResponse.ResultInterface;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRes implements ResultInterface{
	String email;
	String token;
}
