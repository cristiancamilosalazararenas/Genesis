package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for currency conversion.
 * Contains the amount and source currency.
 */
@Getter
@Setter
@NoArgsConstructor
public class ConversionRequest {
    /** Amount to convert */
    private double amount;

    /** Source currency (COP or USD) */
    private String sourceCurrency;
}

