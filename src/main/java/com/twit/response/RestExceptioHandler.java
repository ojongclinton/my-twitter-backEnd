package com.twit.response;
import java.util.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.twit.apiResponse.Code;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
@Slf4j
public class RestExceptioHandler{
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArguementException(HttpServletRequest req, MethodArgumentNotValidException ex){
		List <FieldError> errors = ex.getFieldErrors();
		ArrayList<String> arrErrors = new ArrayList<String>();
		for (FieldError err:errors) {
			arrErrors.add(err.getDefaultMessage());
		}
		return buildResponseEntity(new MultipleErrorResponse(HttpStatus.BAD_REQUEST,arrErrors,Code.FAILURE));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleUnreadableMessage(HttpServletRequest req,HttpMessageNotReadableException ex){
		ArrayList<String> arrErrors = new ArrayList<String>();
		arrErrors.add("Request body is missing");
		return buildResponseEntity(new MultipleErrorResponse(HttpStatus.BAD_REQUEST,arrErrors,Code.FAILURE));
	}
	
	@ExceptionHandler(InvalidCombination.class)
	public ResponseEntity<Object> handleInvalidCombination(HttpServletRequest req,InvalidCombination ex){
		ArrayList<String> arrErrors = new ArrayList<String>();
		arrErrors.add(ex.getMessage());
		return buildResponseEntity(new MultipleErrorResponse(HttpStatus.UNAUTHORIZED,arrErrors,Code.FAILURE));
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleResourceNotFound(HttpServletRequest req,NoSuchElementException ex){
		ArrayList<String> arrErrors = new ArrayList<String>();
		arrErrors.add("The resource you are trying to acces was not found");
		return buildResponseEntity(new MultipleErrorResponse(HttpStatus.NOT_FOUND,arrErrors,Code.FAILURE));
	}
	
	@ExceptionHandler(UnauthorizedAction.class)
	public ResponseEntity<Object> handleUnauthorizedAction(HttpServletRequest req,UnauthorizedAction ex){
		ArrayList<String> arrErrors = new ArrayList<String>();
		arrErrors.add(ex.getMessage());
		return buildResponseEntity(new MultipleErrorResponse(HttpStatus.UNAUTHORIZED,arrErrors,Code.FAILURE));
	}
	
	private ResponseEntity<Object>buildResponseEntity(MultipleErrorResponse err){
		return new ResponseEntity<>(err,err.getStatus());
	}
}
