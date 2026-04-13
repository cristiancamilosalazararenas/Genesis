package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.admin.*;
import com.breaze.genesis.services.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IAdminService adminService;

    // ── USERS ─────────────────────────────────────────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<Page<UserAdminResponse>> getAllUsers(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllUsers(PageRequest.of(page, size)));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<AdminMessageResponse> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(adminService.updateUserStatus(id, request));
    }

    @PatchMapping("/users/{id}/tokens")
    public ResponseEntity<TokenRechargeResponse> rechargeTokens(
            @PathVariable Long id,
            @Valid @RequestBody TokenRechargeRequest request) {
        return ResponseEntity.ok(adminService.rechargeTokens(id, request));
    }

    // ── OPERATIONS ────────────────────────────────────────────────────────────

    @PatchMapping("/operations/{code}/status")
    public ResponseEntity<AdminMessageResponse> updateOperationStatus(
            @PathVariable String code,
            @Valid @RequestBody OperationStatusRequest request) {
        return ResponseEntity.ok(adminService.updateOperationStatus(code, request));
    }

    // ── EXCHANGE RATE ─────────────────────────────────────────────────────────

    @GetMapping("/exchange-rate")
    public ResponseEntity<ExchangeRateResponse> getExchangeRate() {
        return ResponseEntity.ok(adminService.getExchangeRate());
    }

    @PutMapping("/exchange-rate")
    public ResponseEntity<ExchangeRateResponse> updateExchangeRate(
            @Valid @RequestBody ExchangeRateRequest request) {
        return ResponseEntity.ok(adminService.updateExchangeRate(request));
    }

    // ── METRICS ───────────────────────────────────────────────────────────────

    @GetMapping("/metrics/tokens-per-day")
    public ResponseEntity<List<TokensPerDayResponse>> getTokensPerDay(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(adminService.getTokensPerDay(from, to));
    }

    @GetMapping("/metrics/top-operations")
    public ResponseEntity<Page<TopOperationResponse>> getTopOperations(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getTopOperations(PageRequest.of(page, size)));
    }

    @GetMapping("/metrics/top-users")
    public ResponseEntity<Page<TopUserResponse>> getTopUsers(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getTopUsers(PageRequest.of(page, size)));
    }
}