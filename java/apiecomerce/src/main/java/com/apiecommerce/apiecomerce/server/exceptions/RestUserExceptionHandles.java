package com.apiecommerce.apiecomerce.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apiecommerce.apiecomerce.server.exceptions.exceptionModels.UserException;
import com.apiecommerce.apiecomerce.server.exceptions.exceptionModels.UserNotFoundExceptionHandler;
import com.apiecommerce.apiecomerce.server.exceptions.exceptionModels.UserForbiddenExceptionHandler;

@ControllerAdvice
public class RestUserExceptionHandles extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundExceptionHandler.class)
    public ResponseEntity<UserException> userNotFoundAuthorization(
            UserNotFoundExceptionHandler exception) {
        UserException u1 = new UserException();
        u1.setMessage("Verify yours credencials and try again");
        u1.setHttpStatus(HttpStatus.BAD_REQUEST);
        u1.setErro(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(u1);
    }

    @ExceptionHandler(UserForbiddenExceptionHandler.class)
    public ResponseEntity<UserException> userInvalid(UserNotFoundExceptionHandler exception) {
        UserException u1 = new UserException();
        u1.setMessage("User invalid, try again or created new user");
        u1.setHttpStatus(HttpStatus.BAD_REQUEST);
        u1.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(u1);
    }

}
