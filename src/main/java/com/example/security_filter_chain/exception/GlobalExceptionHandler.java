package com.example.security_filter_chain.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity handleGenericException(Exception e, Throwable t) {
        System.out.println("########################### From global exception handler");
        log.error(e.getMessage(), t);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
