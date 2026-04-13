package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRequest {
    private double amount;
    private String sourceCurrency; // "COP" or "USD"
}


