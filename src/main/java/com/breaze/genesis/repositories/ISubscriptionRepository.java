package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para la entidad Subscription.
 * Permite realizar operaciones de persistencia y consultas
 * relacionadas con las suscripciones de los usuarios.
 */
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Busca una suscripción asociada a un usuario por su identificador.
     *
     * @param userId identificador del usuario
     * @return un Optional que contiene la suscripción si existe
     */
    Optional<Subscription> findByUserId(Long userId);
}