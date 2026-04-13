package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entity representing an operation in the system.
 * Each operation has a unique code, name, base cost in tokens, and active state.
 * Operations are linked to transactions that record their execution.
 */
@Entity
@Table(name = "operations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "transactions")
public class Operation {

    /** Unique code of the operation */
    @Id
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    /** Name of the operation */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Base cost in tokens required to execute the operation */
    @Column(name = "cost_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal costBase;

    /** Indicates if the operation is currently active */
    @Column(name = "active", nullable = false)
    private Boolean active;

    /** List of transactions associated with this operation */
    @OneToMany(mappedBy = "operation")
    private List<Transaction> transactions;
}