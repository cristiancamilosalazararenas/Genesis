package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Response DTO for BMI calculation.
 * Provides BMI value, category, healthy weight range and difference.
 */
@Getter
@Setter
@NoArgsConstructor
public class ImcResponse {
    /** Calculated BMI value */
    private double bmi;

    /** Category (Underweight, Normal, Overweight, Obesity) */
    private String category;

    /** Minimum healthy weight */
    private double minHealthyWeight;

    /** Maximum healthy weight */
    private double maxHealthyWeight;

    /** Difference between current and maximum healthy weight */
    private double weightDifference;
}

