package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Response DTO for currency conversion.
 * Provides result, direction, applied rate and last update time.
 */
@Getter
@Setter
@NoArgsConstructor
public class ConversionResponse {
    /** Converted amount */
    private double result;

    /** Conversion direction (COP->USD or USD->COP) */
    private String direction;

    /** Applied exchange rate */
    private double appliedRate;

    /** Last update timestamp */
    private LocalDateTime lastUpdate;
}
