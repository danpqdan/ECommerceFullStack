package com.apiecommerce.apiecomerce.server.exceptions.exceptionModels;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserException {
    String message;
    String erro;
    HttpStatus httpStatus;
}
