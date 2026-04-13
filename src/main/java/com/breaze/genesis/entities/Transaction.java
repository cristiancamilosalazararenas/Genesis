package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entity representing a transaction in the system.
 * Each transaction records the tokens consumed, type of operation,
 * date of execution, and links to the user and operation.
 */
@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "operation"})
public class Transaction {

    /** Primary key identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Number of tokens consumed in this transaction */
    @Column(name = "tokens_consumed", nullable = false)
    private Integer tokensConsumed;

    /** Type of transaction (e.g., OPERATION, SUBSCRIPTION) */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /** Date and time of the transaction */
    @Column(name = "date", insertable = false, updatable = false)
    private LocalDateTime date;

    /** User associated with the transaction */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Operation associated with the transaction */
    @ManyToOne
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;
}