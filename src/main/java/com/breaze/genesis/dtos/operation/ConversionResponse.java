package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConversionResponse {
    private double result;
    private String direction;
    private double appliedRate;
    private LocalDateTime lastUpdate;
}
