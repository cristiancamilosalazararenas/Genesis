package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Plan.
 * Permite realizar operaciones de persistencia y consulta sobre los planes
 * mediante las funcionalidades proporcionadas por Spring Data JPA.
 */
public interface IPlanRepository extends JpaRepository<Plan,Long> {
}