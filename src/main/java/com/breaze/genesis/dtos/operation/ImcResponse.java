package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ImcResponse {
    private double bmi;
    private String category;
    private double minHealthyWeight;
    private double maxHealthyWeight;
    private double weightDifference;
}


