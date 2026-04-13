package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.UserResponse;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.IUserRepository;
import com.breaze.genesis.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of IUserService.
 *
 * Provides operations related to user data retrieval.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    /**
     * Retrieves the user profile using the user ID.
     *
     * @param id unique identifier of the user
     * @return UserResponse containing user information
     * @throws RuntimeException if the user is not found
     */
    @Override
    public UserResponse getProfileById(Long id) {
        System.out.println("BUSCANDO USER ID: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setTokens(user.getBalanceTokens());
        response.setStatus(user.getStatus());

        return response;
    }
}