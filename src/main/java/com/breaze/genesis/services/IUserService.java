package com.breaze.genesis.services;

import com.breaze.genesis.dtos.*;

/*
Extiende UserDetailsService de Spring Security.
Esto es importante porque Spring necesita saber cómo cargar
un usuario por email
*/
public interface IUserService{
    UserResponse getProfileById(Long id );
}