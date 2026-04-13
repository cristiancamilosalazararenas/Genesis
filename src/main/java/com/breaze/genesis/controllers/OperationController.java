package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.operation.*;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.IUserRepository;
import com.breaze.genesis.services.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    /** Repository to resolve userId from email */
    @Autowired
    private IUserRepository userRepository;

    /**
     * Helper method to resolve userId from authenticated principal.
     *
     * @param userDetails authenticated user details
     * @return userId from database
     */
    private Long resolveUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    /**
     * Endpoint to calculate credit payment details.
     */
    @PostMapping("/credit")
    public ResponseEntity<CreditResponse> calculateCredit(@RequestBody CreditRequest request,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails);
        return ResponseEntity.ok(operationService.calculateCredit(request, userId));
    }

    /**
     * Endpoint to convert currency between COP and USD.
     */
    @PostMapping("/conversion")
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestBody ConversionRequest request,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails);
        return ResponseEntity.ok(operationService.convertCurrency(request, userId));
    }

    /**
     * Endpoint to calculate Body Mass Index (BMI).
     */
    @PostMapping("/bmi")
    public ResponseEntity<ImcResponse> calculateBmi(@RequestBody ImcRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails);
        return ResponseEntity.ok(operationService.calculateBmi(request, userId));
    }

    /**
     * Endpoint to calculate sleep cycles.
     */
    @PostMapping("/sleep")
    public ResponseEntity<SleepResponse> calculateSleep(@RequestBody SleepRequest request,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails);
        return ResponseEntity.ok(operationService.calculateSleep(request, userId));
    }
}