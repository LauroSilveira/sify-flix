package com.lauro.sifyflixapi.restcontroller.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ApiMessage {
    private HttpStatus httpStatus;
    private String message;
}
