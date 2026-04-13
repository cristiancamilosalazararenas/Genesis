package com.breaze.genesis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Objeto de transferencia de datos que representa la respuesta de autenticación.
 * Contiene la información necesaria que se devuelve al usuario después de un
 * proceso exitoso de login o registro.
 */
@Data
@NoArgsConstructor
public class AuthResponse {

    /**
     * Token JWT generado para la autenticación del usuario.
     */
    private String token;

    /**
     * Correo electrónico del usuario autenticado.
     */
    private String email;

    /**
     * Plan asociado al usuario.
     */
    private String plan;

    /**
     * Cantidad de tokens disponibles del usuario.
     */
    private Integer tokens;

    /**
     * Lista de roles asignados al usuario.
     */
    private List<String> roles;
}