package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

/**
 * Entidad que representa un rol dentro del sistema.
 * Define los diferentes tipos de roles que pueden ser asignados a los usuarios.
 */
@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
public class Rol {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Nombre del rol.
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * Lista de usuarios asociados a este rol.
     */
    @OneToMany(mappedBy = "rol")
    private List<User> users;
}