package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.operation.*;
import com.breaze.genesis.services.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for operations.
 * Exposes endpoints for credit calculation, currency conversion,
 * BMI calculation and sleep calculation.
 *
 * Each endpoint consumes tokens and executes the corresponding business logic
 * defined in the OperationService.
 */
@RestController
@RequestMapping("/operations")
public class OperationController {

    /** Service layer for operations */
    @Autowired
    private IOperationService operationService;

    /**
     * Endpoint to calculate credit payment details.
     *
     * @param request CreditRequest with financing details
     * @param userDetails Authenticated user details
     * @return CreditResponse with monthly payment, total paid and interests
     */
    @PostMapping("/credit")
    public ResponseEntity<CreditResponse> calculateCredit(@RequestBody CreditRequest request,
                                                          @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateCredit(request, userId));
    }

    /**
     * Endpoint to convert currency between COP and USD.
     *
     * @param request ConversionRequest with amount and source currency
     * @param userDetails Authenticated user details
     * @return ConversionResponse with result and applied rate
     */
    @PostMapping("/conversion")
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestBody ConversionRequest request,
                                                              @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.convertCurrency(request, userId));
    }

    /**
     * Endpoint to calculate Body Mass Index (BMI).
     *
     * @param request ImcRequest with weight and height
     * @param userDetails Authenticated user details
     * @return ImcResponse with BMI value, category and healthy range
     */
    @PostMapping("/bmi")
    public ResponseEntity<ImcResponse> calculateBmi(@RequestBody ImcRequest request,
                                                    @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateBmi(request, userId));
    }

    /**
     * Endpoint to calculate sleep cycles.
     *
     * @param request SleepRequest with mode and reference time
     * @param userDetails Authenticated user details
     * @return SleepResponse with calculated times and quality
     */
    @PostMapping("/sleep")
    public ResponseEntity<SleepResponse> calculateSleep(@RequestBody SleepRequest request,
                                                        @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateSleep(request, userId));
    }
}