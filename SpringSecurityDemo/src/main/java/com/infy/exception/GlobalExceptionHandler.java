package com.infy.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	   @ExceptionHandler(ProductException.class)
	    public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(ProductException exception, WebRequest webRequest) {
	        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
	        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	
	   }	

}
