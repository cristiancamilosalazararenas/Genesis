package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class SleepResponse {
    private String calculatedTime;
    private double totalHours;
    private String quality; // "Recommended", "Ideal", etc.
}



