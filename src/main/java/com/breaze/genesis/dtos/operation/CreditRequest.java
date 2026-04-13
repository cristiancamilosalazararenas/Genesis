package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for credit calculation.
 * Contains the financing amount, number of installments and monthly interest rate.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditRequest {
    /** Amount to finance */
    private double price;

    /** Number of monthly installments */
    private int installments;

    /** Monthly interest rate in percentage */
    private double monthlyRate;
}
