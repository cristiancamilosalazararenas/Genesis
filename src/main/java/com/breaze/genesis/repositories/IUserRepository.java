package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad User.
 * Permite realizar operaciones de persistencia y consultas
 * relacionadas con los usuarios del sistema.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email correo electrónico del usuario
     * @return un Optional que contiene el usuario si existe
     */
    Optional<User> findByEmail(String email);
}