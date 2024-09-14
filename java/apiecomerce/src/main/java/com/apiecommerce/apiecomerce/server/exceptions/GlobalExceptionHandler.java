package com.apiecommerce.apiecomerce.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        // Log ou outra lógica personalizada
        return new ResponseEntity<>("Erro: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
