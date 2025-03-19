package com.tecnova.task.api.controller;

import com.tecnova.task.api.generated.AuthApi;
import com.tecnova.task.api.model.User;
import com.tecnova.task.api.service.authentication.AuthenticationService;
import com.tecnova.task.api.service.authentication.JwtService;

import com.tecnova.task.dto.generated.TokenResponse;
import com.tecnova.task.dto.generated.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
public class AuthenticationController implements AuthApi {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<TokenResponse> loginUser(UserRequest userRequest) {
        User authenticatedUser = authenticationService.authenticate(userRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        var token =TokenResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();


        return ResponseEntity.ok(token);
    }




}
