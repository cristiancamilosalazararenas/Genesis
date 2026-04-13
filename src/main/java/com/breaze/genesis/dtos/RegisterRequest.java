package com.breaze.genesis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos que representa la solicitud de registro.
 * Contiene la información básica necesaria para crear un nuevo usuario en el sistema.
 */
@Data
@NoArgsConstructor
public class RegisterRequest {

    /**
     * Correo electrónico del nuevo usuario.
     */
    private String email;

    /**
     * Contraseña del nuevo usuario.
     */
    private String password;
}