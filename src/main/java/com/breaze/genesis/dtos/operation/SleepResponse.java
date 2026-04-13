package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Response DTO for sleep calculation.
 * Provides calculated time, total hours and sleep quality.
 */
@Getter
@Setter
@NoArgsConstructor
public class SleepResponse {
    /** Calculated sleep/wake time */
    private String calculatedTime;

    /** Total hours of sleep */
    private double totalHours;

    /** Sleep quality (Recommended, Ideal, etc.) */
    private String quality;
}
