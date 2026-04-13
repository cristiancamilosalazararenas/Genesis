package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Response DTO for credit calculation.
 * Provides monthly payment, total paid, total interest and amortization table.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditResponse {
    /** Monthly payment value */
    private double monthlyPayment;

    /** Total amount paid */
    private double totalPaid;

    /** Total interest paid */
    private double totalInterest;

    /** Amortization table details */
    private List<String> amortizationTable;
}