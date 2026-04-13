package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Operation.
 * Proporciona métodos para realizar operaciones CRUD sobre las operaciones
 * utilizando Spring Data JPA.
 */
public interface IOperation extends JpaRepository<Operation, Long> {
}