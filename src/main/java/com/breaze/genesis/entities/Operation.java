package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad que representa una operación dentro del sistema.
 * Define las características principales de una operación,
 * incluyendo su costo base, estado y las transacciones asociadas.
 */
@Entity
@Table(name = "operations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "transactions")
public class Operation {

    /**
     * Código único que identifica la operación.
     */
    @Id
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /**
     * Nombre descriptivo de la operación.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Costo base de la operación.
     */
    @Column(name = "cost_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal costBase;

    /**
     * Indica si la operación está activa.
     */
    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Lista de transacciones asociadas a esta operación.
     */
    @OneToMany(mappedBy = "operation")
    private List<Transaction> transactions;
}