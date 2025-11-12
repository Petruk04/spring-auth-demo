package com.example.autentication_app.controller;

import com.example.autentication_app.dto.AuthenticationRequest;
import com.example.autentication_app.dto.AuthenticationResponse;
import com.example.autentication_app.service.AuthenticationService;
import com.example.autentication_app.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }

}
