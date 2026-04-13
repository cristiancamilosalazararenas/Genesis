package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa un usuario dentro del sistema.
 * Contiene la información principal del usuario, su estado,
 * tokens disponibles y las relaciones con otras entidades.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Correo electrónico del usuario.
     */
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    /**
     * Contraseña del usuario en formato encriptado.
     */
    @Column (nullable = false, length = 255)
    private String password;

    /**
     * Cantidad de tokens disponibles del usuario.
     */
    @Column(name = "balance_tokens")
    private Integer balanceTokens = 0;

    /**
     * Estado actual del usuario.
     */
    @Column(length = 50)
    private String status;

    /**
     * Fecha de creación del usuario.
     */
    @Column(name="creation_date", updatable = false)
    private LocalDateTime creationDate;

    /**
     * Fecha de última actualización del usuario.
     */
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /**
     * Lista de suscripciones asociadas al usuario.
     */
    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;

    /**
     * Rol asignado al usuario.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Rol rol;
}