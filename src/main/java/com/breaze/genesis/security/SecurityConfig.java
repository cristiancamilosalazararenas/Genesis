package com.breaze.genesis.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Define las reglas de autenticación, autorización y configuración
 * de filtros para el manejo de seguridad con JWT.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Filtro personalizado para la validación de tokens JWT.
     */
    private final JwtFilter jwtFilter;

    /**
     * Servicio para cargar los detalles del usuario.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param http configuración de seguridad HTTP
     * @return cadena de filtros configurada
     * @throws Exception en caso de error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        /**
                         * Endpoints públicos accesibles sin autenticación.
                         */
                        .requestMatchers("/auth/**").permitAll()

                        /**
                         * Endpoints exclusivos para administradores.
                         */
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        /**
                         * Todos los demás endpoints requieren autenticación.
                         */
                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex
                        /**
                         * Manejo de errores de autenticación.
                         */
                        .authenticationEntryPoint((req, res, e) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED))//401 No autenticado
                        /**
                         * Manejo de errores de autorización.
                         */
                        .accessDeniedHandler((req, res, e) ->
                                res.sendError(HttpServletResponse.SC_FORBIDDEN))//403 sin permisos
                )

                /**
                 * Configuración del proveedor de autenticación.
                 */
                .authenticationProvider(authenticationProvider())

                /**
                 * Registro del filtro JWT antes del filtro de autenticación estándar.
                 */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * Configura el proveedor de autenticación basado en DAO.
     *
     * @return proveedor de autenticación configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Proporciona el gestor de autenticación.
     *
     * @param config configuración de autenticación
     * @return AuthenticationManager
     * @throws Exception en caso de error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el codificador de contraseñas.
     *
     * @return instancia de PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}