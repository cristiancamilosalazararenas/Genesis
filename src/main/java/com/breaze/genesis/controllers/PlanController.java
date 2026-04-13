package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.plan.PlanRequest;
import com.breaze.genesis.dtos.plan.PlanResponse;
import com.breaze.genesis.dtos.plan.SubscriptionResponse;
import com.breaze.genesis.services.IPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final IPlanService planService;

    /** Todos los roles pueden listar planes */
    @GetMapping
    public ResponseEntity<List<PlanResponse>> findAll() {
        return ResponseEntity.ok(planService.findAll());
    }

    /** Solo ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.findById(id));
    }

    /** Solo ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PlanResponse> create(@Valid @RequestBody PlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.create(request));
    }

    /** Solo ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PlanRequest request) {
        return ResponseEntity.ok(planService.update(id, request));
    }

    /** Solo ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** USER autenticado se suscribe a un plan */
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribe(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(planService.subscribe(id, authentication.getName()));
    }
}