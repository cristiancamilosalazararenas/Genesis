package com.breaze.genesis.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro encargado de procesar la autenticación basada en JWT en cada solicitud.
 * Extrae el token del encabezado Authorization, lo valida y establece
 * la autenticación en el contexto de Spring Security.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Utilidad para manejo de tokens JWT.
     */
    private final JwtUtil jwtUtil;

    /**
     * Servicio para cargar los detalles del usuario.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Método que se ejecuta en cada solicitud HTTP para validar el token JWT
     * y establecer la autenticación correspondiente.
     *
     * @param request solicitud HTTP entrante
     * @param response respuesta HTTP
     * @param chain cadena de filtros
     * @throws ServletException en caso de error en el servlet
     * @throws IOException en caso de error de entrada/salida
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        /**
         * Obtiene el encabezado Authorization de la solicitud.
         */
        String header = request.getHeader("Authorization");

        /**
         * Si no existe token o no tiene el formato Bearer, continúa sin autenticar.
         */
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        /**
         * Extrae el token eliminando el prefijo Bearer.
         */
        String token = header.substring(7);

        try {
            /**
             * Extrae el nombre de usuario (email) desde el token.
             */
            String username = jwtUtil.extractUsername(token);

            /**
             * Verifica que el usuario no esté autenticado previamente.
             */
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                /**
                 * Carga los detalles del usuario desde la base de datos.
                 */
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                /**
                 * Valida el token contra los datos del usuario.
                 */
                if (jwtUtil.isTokenValid(token, userDetails)) {

                    /**
                     * Crea el objeto de autenticación con los permisos del usuario.
                     */
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    /**
                     * Establece la autenticación en el contexto de seguridad.
                     */
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (Exception e) {
            /**
             * Manejo de errores durante la validación del token.
             */
            System.out.println("JWT error: " + e.getMessage());
        }

        /**
         * Continúa con la cadena de filtros.
         */
        chain.doFilter(request, response);
    }
}