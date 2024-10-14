package com.apiecommerce.apiecomerce.server.exceptions.exceptionModels;

public class UserForbiddenExceptionHandler extends RuntimeException {

    public UserForbiddenExceptionHandler() {
        super("User not valid, irregular param");
    }

    public UserForbiddenExceptionHandler(String message) {
        super(message);
    }
}
