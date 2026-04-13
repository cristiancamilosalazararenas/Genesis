package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreditResponse {
    private double monthlyPayment;
    private double totalPaid;
    private double totalInterest;
    private List<String> amortizationTable;
}

