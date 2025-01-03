package com.Icwd.user.service.exceptions;


import com.Icwd.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResouceNotFoundException ex){

     String message= ex.getMessage();
    ApiResponse response = ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
    return new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }
}
