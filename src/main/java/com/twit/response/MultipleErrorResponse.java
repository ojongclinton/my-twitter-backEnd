package com.twit.response;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twit.apiResponse.Code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MultipleErrorResponse {
	
	private HttpStatus status;
	private Code code;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyMMdd hh:mm:ss")
	private LocalDateTime timeStamp;
	private ArrayList<String> messages;
	
	public MultipleErrorResponse() {
		this.timeStamp = LocalDateTime.now();
	}
	
	public MultipleErrorResponse(HttpStatus stat) {
		this.status = stat;
	}
	
	public MultipleErrorResponse(HttpStatus stat, ArrayList<String> msg,Code code) {
		this();
		this.status = stat;
		this.messages = msg;
		this.code = code;
	}
}
