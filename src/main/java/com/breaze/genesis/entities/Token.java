package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the token balance of a user.
 * Tokens are consumed when executing operations.
 * This entity stores the current balance for each user.
 */
@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Token {

    /** Primary key identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User identifier linked to this token balance */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Current token balance available for the user */
    @Column(name = "balance", nullable = false)
    private int balance;
}
