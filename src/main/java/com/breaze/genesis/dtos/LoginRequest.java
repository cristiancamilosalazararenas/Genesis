package com.breaze.genesis.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de transferencia de datos que representa la solicitud de inicio de sesión.
 * Contiene las credenciales necesarias para autenticar a un usuario en el sistema.
 */
@Getter
@Setter
public class LoginRequest {

    /**
     * Correo electrónico del usuario que intenta autenticarse.
     */
    private String email;

    /**
     * Contraseña del usuario.
     */
    private String password;
}