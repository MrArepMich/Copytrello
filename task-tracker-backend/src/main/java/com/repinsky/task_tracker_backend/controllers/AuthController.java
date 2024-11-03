package com.repinsky.task_tracker_backend.controllers;

import com.repinsky.task_tracker_backend.dto.JWTRequest;
import com.repinsky.task_tracker_backend.dto.JWTResponse;
import com.repinsky.task_tracker_backend.dto.RegisterUserDto;
import com.repinsky.task_tracker_backend.dto.StringResponse;
import com.repinsky.task_tracker_backend.exceptions.InputDataException;
import com.repinsky.task_tracker_backend.jwt.JWTTokenUtil;
import com.repinsky.task_tracker_backend.producer.RegistrationProducer;
import com.repinsky.task_tracker_backend.services.CustomUserDetailsService;
import com.repinsky.task_tracker_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JWTRequest authRequest) {
        logger.info("Received authentication request for user email: " + authRequest.getEmail());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        logger.info("Generated token for user with email: " + authRequest.getEmail());

        return ResponseEntity.ok(new JWTResponse(token, customUserDetailsService.getRoles(authRequest.getEmail())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserDto registerUserDto) throws InputDataException {
        userService.createNewUser(registerUserDto);
        return ResponseEntity.ok(new StringResponse("User with email " + registerUserDto.getEmail()
                + " is successfully signed up"));
    }
}
