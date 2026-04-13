package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * Entidad que representa una transacción dentro del sistema.
 * Registra el consumo de tokens por parte de un usuario al realizar
 * una operación específica.
 */
@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "operation"})
public class Transaction {

    /**
     * Identificador único de la transacción.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Cantidad de tokens consumidos en la transacción.
     */
    @Column(name = "tokens_consumed", nullable = false)
    private Integer tokensConsumed;

    /**
     * Tipo de transacción realizada.
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * Fecha en la que se realizó la transacción.
     */
    @Column(name = "date", insertable = false, updatable = false)
    private LocalDateTime date;

    /**
     * Usuario que realizó la transacción.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Operación asociada a la transacción.
     */
    @ManyToOne
    @JoinColumn(name = "operation_code", nullable = false)
    private Operation operation;
}