package com.lauro.sifyflixapi.restcontrolleradvice;

import com.lauro.sifyflixapi.restcontroller.login.LoginRestController;
import com.lauro.sifyflixapi.restcontrolleradvice.dto.ApiMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = LoginRestController.class)
public class LoginRestControllerAdvice {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiMessage> handleRecordNotFoundException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiMessage.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .build());
    }
}
