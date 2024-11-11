package com.apiecommerce.apiecomerce.server.exceptions.exceptionModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundExceptionHandler extends RuntimeException {

    public UserNotFoundExceptionHandler() {
        super("Usuario não encontrado");
    }

    public UserNotFoundExceptionHandler(String message) {
        super(message);
    }
}
