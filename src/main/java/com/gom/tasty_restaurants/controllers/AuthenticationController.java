package com.gom.tasty_restaurants.controllers;

import com.gom.tasty_restaurants.dto.AuthenticationDTO;
import com.gom.tasty_restaurants.dto.LoginResponseDTO;
import com.gom.tasty_restaurants.dto.RegisterDTO;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.model.UserRole;
import com.gom.tasty_restaurants.repositories.UserRepository;
import com.gom.tasty_restaurants.services.AuthenticationService;
import com.gom.tasty_restaurants.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        LoginResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        authenticationService.register(data);
        return ResponseEntity.ok().build();
    }
}

