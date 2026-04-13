package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConversionRequest {
    private double value;
    private String fromUnit;
    private String toUnit;
}

