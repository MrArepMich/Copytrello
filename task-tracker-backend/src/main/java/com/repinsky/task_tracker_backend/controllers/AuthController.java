package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.dto.JWTRequest;
import com.repinsky.task_tracker_backend.dto.JWTResponse;
import com.repinsky.task_tracker_backend.dto.RegisterUserRequest;
import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.exceptions.InputDataException;
import com.repinsky.task_tracker_backend.jwt.JWTTokenUtil;
import com.repinsky.task_tracker_backend.services.CustomUserDetailsService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JWTRequest authRequest) {
        log.info("Received authentication request for user email: {}", authRequest.getEmail());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        log.info("Generated token for user with email: {}", authRequest.getEmail());

        return ResponseEntity.ok(new JWTResponse(token, customUserDetailsService.getRoles(authRequest.getEmail())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserRequest registerUserRequest) throws InputDataException {
        log.info("Request to create new user: email -> {}", registerUserRequest.getEmail());
        userService.createNewUser(registerUserRequest);
        return ResponseEntity.ok(new StringResponse("User with email '" + registerUserRequest.getEmail() + "' registered successfully"));
    }
}
