package com.breaze.genesis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos que representa la respuesta del registro de usuario.
 * Extiende de AuthResponse para reutilizar la información de autenticación
 * generada después de crear un nuevo usuario en el sistema.
 */
@Data
@NoArgsConstructor
public class RegisterResponse extends AuthResponse  {
}