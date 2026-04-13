package com.breaze.genesis.services;

import com.breaze.genesis.dtos.operation.*;

/**
 * Service interface for operations.
 * Defines methods for credit calculation, currency conversion, BMI calculation and sleep calculation.
 */
public interface IOperationService {

    /**
     * Calculates credit payment details.
     *
     * @param request CreditRequest with financing details
     * @param userId User identifier
     * @return CreditResponse with monthly payment, total paid and interests
     */
    CreditResponse calculateCredit(CreditRequest request, Long userId);

    /**
     * Converts currency between COP and USD.
     *
     * @param request ConversionRequest with amount and source currency
     * @param userId User identifier
     * @return ConversionResponse with result and applied rate
     */
    ConversionResponse convertCurrency(ConversionRequest request, Long userId);

    /**
     * Calculates Body Mass Index (BMI).
     *
     * @param request ImcRequest with weight and height
     * @param userId User identifier
     * @return ImcResponse with BMI value, category and healthy range
     */
    ImcResponse calculateBmi(ImcRequest request, Long userId);

    /**
     * Calculates sleep cycles based on wake up or bed time.
     *
     * @param request SleepRequest with mode and reference time
     * @param userId User identifier
     * @return SleepResponse with calculated times and quality
     */
    SleepResponse calculateSleep(SleepRequest request, Long userId);
}