package com.lauro.sifyflixapi.restcontrolleradvice;

import com.lauro.sifyflixapi.restcontroller.user.UserRestController;
import com.lauro.sifyflixapi.dto.ApiMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserRestController.class)
public class UserControllerAdvice {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiMessage> handleMethodValidationException(AccessDeniedException ex) {
        return ResponseEntity.badRequest().body(ApiMessage.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .message("User has not privileges for this action")
                .build());
    }
}
