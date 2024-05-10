package com.example.code.controller;

import com.example.code.dto.WebResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<Object>> constraintViolationException(ConstraintViolationException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.builder().errors(e.getMessage()).build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException e) {

        return ResponseEntity.status(e.getStatusCode())
                .body(WebResponse.<String>builder().errors(e.getReason()).build());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<WebResponse<String>> nullPointerException(NullPointerException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder().errors(e.getMessage()).build());
    }
}
