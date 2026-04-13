package com.breaze.genesis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos que representa la respuesta de inicio de sesión.
 * Extiende de AuthResponse para reutilizar la información de autenticación
 * como el token, email, roles y demás datos asociados al usuario.
 */
@Data
@NoArgsConstructor
public class LoginResponse extends AuthResponse{
}