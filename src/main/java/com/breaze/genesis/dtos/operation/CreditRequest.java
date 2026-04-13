package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreditRequest {
    private double amount;
    private double rate;
    private int months;
}

