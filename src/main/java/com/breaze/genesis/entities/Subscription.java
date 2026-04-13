package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * Entidad que representa una suscripción dentro del sistema.
 * Relaciona un usuario con un plan y define el periodo de vigencia,
 * estado y tokens acreditados.
 */
@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "plan"})
public class Subscription {

    /**
     * Identificador único de la suscripción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Fecha de inicio de la suscripción.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * Fecha de finalización de la suscripción.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    /**
     * Estado actual de la suscripción.
     */
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    /**
     * Cantidad de tokens acreditados durante la suscripción.
     */
    @Column(name = "accredited_tokens")
    private Integer accreditedTokens;

    /**
     * Usuario asociado a la suscripción.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Plan asociado a la suscripción.
     */
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
}