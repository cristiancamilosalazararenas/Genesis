package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class SleepRequest {
    private String mode; // "WAKE_UP" or "BED_TIME"
    private String time; // HH:mm format
    private int minutesToFallAsleep = 14;
}


