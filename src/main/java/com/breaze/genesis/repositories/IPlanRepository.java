package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlanRepository extends JpaRepository<Plan, Long> {

    /**
     * Verifica si el plan tiene al menos una suscripción ACTIVA.
     * Se usa para bloquear edición/eliminación de planes con suscriptores activos.
     */
    @Query("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.plan.id = :planId AND s.state = 'ACTIVE'")
    boolean hasActiveSubscriptions(@Param("planId") Long planId);
}
