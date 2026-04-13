package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * relacionadas con los roles del sistema.
 */
public interface IRolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre nombre del rol
     * @return un Optional que contiene el rol si existe
     */
    Optional<Rol> findByName(String nombre);
}