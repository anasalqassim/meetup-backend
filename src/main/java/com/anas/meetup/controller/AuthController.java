package com.anas.meetup.controller;


import com.anas.meetup.auth.AuthenticationService;
import com.anas.meetup.payload.AuthResponse;
import com.anas.meetup.payload.AuthenticationRequest;
import com.anas.meetup.payload.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;



    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ){
        if (request == null){
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("your request was wrong check it plz")
                    .build(), HttpStatus.BAD_REQUEST);
        }

        try {
            return ResponseEntity.ok(service.register(request));
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return new ResponseEntity<>(AuthResponse.builder()
                    .message("The Email is already exists")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(AuthResponse.builder()
                    .message(e.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        if (request == null){
            return new ResponseEntity<>(AuthResponse.builder()
                    .httpCode("400")
                    .message("your sent bad request")
                    .build(), HttpStatus.BAD_REQUEST);
        }


        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (BadCredentialsException e){
            e.printStackTrace();
            return new ResponseEntity<>(AuthResponse.builder()
                    .message(e.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
