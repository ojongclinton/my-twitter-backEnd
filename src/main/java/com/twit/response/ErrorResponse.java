package com.twit.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twit.apiResponse.Code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
	
	private HttpStatus status;
	private Code code;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyMMdd hh:mm:ss")
	private LocalDateTime timeStamp;
	private String message;
	
	public ErrorResponse() {
		this.timeStamp = LocalDateTime.now();
	}
	
	public ErrorResponse(HttpStatus stat) {
		this.status = stat;
	}
	
	public ErrorResponse(HttpStatus stat, String msg) {
		this();
		this.status = stat;
		this.message = msg;
	}
}
