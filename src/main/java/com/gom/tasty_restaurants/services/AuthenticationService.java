package com.gom.tasty_restaurants.services;

import com.gom.tasty_restaurants.dto.AuthenticationDTO;
import com.gom.tasty_restaurants.dto.LoginResponseDTO;
import com.gom.tasty_restaurants.dto.RegisterDTO;
import com.gom.tasty_restaurants.model.User;
import com.gom.tasty_restaurants.model.UserRole;
import com.gom.tasty_restaurants.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var token = tokenService.generateToken((User) authentication.getPrincipal());
        return new LoginResponseDTO(token);
    }

    public void register(RegisterDTO data) {
        if (userRepository.findByLogin(data.getLogin()) != null) {
            throw new RuntimeException("Usuário já existe");
        }

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User newUser = new User(data.getName(), data.getLogin(), encryptedPassword, UserRole.USER);
        userRepository.save(newUser);
    }
}
