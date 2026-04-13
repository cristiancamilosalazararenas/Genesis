package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa un plan dentro del sistema.
 * Define las características de un plan, incluyendo la cantidad de tokens otorgados,
 * su estado y las suscripciones asociadas.
 */
@Entity
@Table(name = "plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "subscriptions")
public class Plan {

    /**
     * Identificador único del plan.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long  id;

    /**
     * Nombre del plan.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Cantidad de tokens que otorga el plan.
     */
    @Column(name = "tokens_granted", nullable = false)
    private Integer tokensGranted;

    /**
     * Indica si el plan se encuentra activo.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Fecha de creación del plan.
     */
    @Column(name = "creation_date", insertable = false, updatable = false)
    private LocalDateTime creationDate;

    /**
     * Fecha de última actualización del plan.
     */
    @Column(name = "update_date", insertable = false, updatable = false)
    private LocalDateTime updateDate;

    /**
     * Lista de suscripciones asociadas a este plan.
     */
    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions;
}