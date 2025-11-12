package com.example.autentication_app.mapper;

import com.example.autentication_app.dto.RegisterRequest;
import com.example.autentication_app.model.User;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "role", constant = "USER")
    User toUser(RegisterRequest request, @Context PasswordEncoder passwordEncoder);

}
