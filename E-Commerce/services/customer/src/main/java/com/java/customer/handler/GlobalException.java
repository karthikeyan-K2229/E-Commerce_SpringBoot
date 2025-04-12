package com.java.customer.handler;

import com.java.customer.exception.CustomerNotFoundExceptipn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(CustomerNotFoundExceptipn.class)
    public ResponseEntity<String> handl(CustomerNotFoundExceptipn exp)
    {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exp.getMsg());
    }
}
