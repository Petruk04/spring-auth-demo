package com.example.autentication_app.service;

import com.example.autentication_app.config.JwtService;
import com.example.autentication_app.dto.AuthenticationRequest;
import com.example.autentication_app.dto.AuthenticationResponse;
import com.example.autentication_app.dto.RegisterRequest;
import com.example.autentication_app.exception.UserAlreadyExistsException;
import com.example.autentication_app.mapper.UserMapper;
import com.example.autentication_app.model.Role;
import com.example.autentication_app.model.User;
import com.example.autentication_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "User with email \"" + request.getEmail() + "\" already exists!"
            );
        }

        User user = userMapper.toUser(request, passwordEncoder);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(request.getEmail()));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
