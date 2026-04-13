package com.breaze.genesis.services;

import com.breaze.genesis.dtos.AuthResponse;
import com.breaze.genesis.dtos.LoginRequest;
import com.breaze.genesis.dtos.LoginResponse;
import com.breaze.genesis.dtos.RegisterRequest;

/**
 * Service interface for authentication operations.
 *
 * Defines the contract for user registration and login processes.
 */
public interface IAuthService {

    /**
     * Registers a new user in the system.
     *
     * @param request object containing email and password
     * @return AuthResponse with generated token and user data
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user with credentials.
     *
     * @param request object containing email and password
     * @return AuthResponse with generated token and user data
     */
    AuthResponse login(LoginRequest request);
}