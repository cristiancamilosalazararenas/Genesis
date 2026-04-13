package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.AuthResponse;
import com.breaze.genesis.dtos.LoginRequest;
import com.breaze.genesis.dtos.RegisterRequest;
import com.breaze.genesis.entities.Rol;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.IRolRepository;
import com.breaze.genesis.repositories.IUserRepository;
import com.breaze.genesis.security.JwtUtil;
import com.breaze.genesis.services.IAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of IAuthService.
 *
 * Handles user registration and authentication processes,
 * including password encryption and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final IUserRepository userRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user in the system.
     *
     * @param request contains email and password
     * @return AuthResponse with token and user data
     * @throws RuntimeException if the email already exists or role is not found
     */
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("The email already exists: " + request.getEmail());
        }

        Rol rol = rolRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");
        user.setRol(rol);
        user.setBalanceTokens(0);
        userRepository.save(user);

        UserDetails userDetails = buildUserDetails(user);

        return buildResponse(userDetails, user);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param request contains login credentials
     * @return AuthResponse with token and user data
     * @throws RuntimeException if the user is not found
     */
    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserDetails userDetails = buildUserDetails(user);

        return buildResponse(userDetails, user);
    }

    /**
     * Builds a UserDetails object from a User entity.
     *
     * @param user entity from database
     * @return UserDetails for Spring Security
     */
    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol().getName())
                .build();
    }

    /**
     * Builds the authentication response with JWT token.
     *
     * @param userDetails authenticated user details
     * @param user entity from database
     * @return AuthResponse with token and user info
     */
    private AuthResponse buildResponse(UserDetails userDetails, User user) {
        String token = jwtUtil.generateToken(userDetails);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setTokens(user.getBalanceTokens());
        response.setRoles(List.of(user.getRol().getName()));
        return response;
    }
}