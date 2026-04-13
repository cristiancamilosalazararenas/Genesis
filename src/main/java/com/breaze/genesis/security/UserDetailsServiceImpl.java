package com.breaze.genesis.security;

import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Service that implements Spring Security authentication logic.
 *
 * This class is responsible for loading user details from the database
 * using the email as identifier, which is required for authentication.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    /**
     * Loads a user from the database using the email.
     *
     * @param email user email used as username
     * @return UserDetails object required by Spring Security
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRol().getName())
                .build();
    }
}