package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.operation.*;
import com.breaze.genesis.services.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private IOperationService operationService;

    @PostMapping("/credit")
    public ResponseEntity<CreditResponse> calculateCredit(@RequestBody CreditRequest request,
                                                          @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateCredit(request, userId));
    }

    @PostMapping("/conversion")
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestBody ConversionRequest request,
                                                              @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.convertCurrency(request, userId));
    }

    @PostMapping("/bmi")
    public ResponseEntity<ImcResponse> calculateBmi(@RequestBody ImcRequest request,
                                                    @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateBmi(request, userId));
    }

    @PostMapping("/sleep")
    public ResponseEntity<SleepResponse> calculateSleep(@RequestBody SleepRequest request,
                                                        @AuthenticationPrincipal Object userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(operationService.calculateSleep(request, userId));
    }
}