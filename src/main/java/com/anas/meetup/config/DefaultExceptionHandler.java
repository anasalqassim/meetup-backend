package com.anas.meetup.config;

import com.anas.meetup.payload.AuthResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, AccessDeniedException.class,AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<AuthResponse> handleAuthenticationException(Exception ex) {

        AuthResponse re =  AuthResponse
                .builder()
                .message("Authentication failed :"+ex.getMessage())
                .httpCode(HttpStatus.UNAUTHORIZED.toString())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }
}
