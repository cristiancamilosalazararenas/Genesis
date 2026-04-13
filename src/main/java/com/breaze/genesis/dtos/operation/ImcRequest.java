package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for BMI calculation.
 * Contains weight in kilograms and height in centimeters.
 */
@Getter
@Setter
@NoArgsConstructor
public class ImcRequest {
    /** Weight in kilograms */
    private double weightKg;

    /** Height in centimeters */
    private double heightCm;
}