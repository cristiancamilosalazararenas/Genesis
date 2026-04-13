package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Operation.
 * Proporciona métodos para realizar operaciones CRUD sobre las operaciones
 * utilizando Spring Data JPA.
 */
@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long> {
}
