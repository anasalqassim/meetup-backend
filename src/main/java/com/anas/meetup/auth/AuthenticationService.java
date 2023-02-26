package com.anas.meetup.auth;


import com.anas.meetup.config.JwtService;
import com.anas.meetup.model.User;
import com.anas.meetup.payload.AuthResponse;
import com.anas.meetup.payload.AuthenticationRequest;
import com.anas.meetup.payload.RegisterRequest;
import com.anas.meetup.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER).build();

        userRepo.save(user);

        var token = jwtService.generateToken(user);
        return  AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse authenticate(AuthenticationRequest request) {

        manager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        return  AuthResponse.builder()
                .token(token)
                .build();

    }
}
