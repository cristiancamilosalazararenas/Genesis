package com.breaze.genesis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos que representa la información del usuario
 * que se devuelve como respuesta en las operaciones del sistema.
 */
@Data
@NoArgsConstructor
public class UserResponse {

    /**
     * Identificador único del usuario.
     */
    private int id;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Cantidad de tokens disponibles del usuario.
     */
    private Integer tokens;

    /**
     * Plan asociado al usuario.
     */
    private String plan;

    /**
     * Estado actual del usuario.
     */
    private String status;
}