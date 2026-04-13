package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.plan.PlanRequest;
import com.breaze.genesis.dtos.plan.PlanResponse;
import com.breaze.genesis.dtos.plan.SubscriptionResponse;
import com.breaze.genesis.entities.Plan;
import com.breaze.genesis.entities.Subscription;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.IPlanRepository;
import com.breaze.genesis.repositories.ISubscriptionRepository;
import com.breaze.genesis.repositories.IUserRepository;
import com.breaze.genesis.services.IPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements IPlanService {

    private final IPlanRepository       planRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final IUserRepository       userRepository;

    // ── CRUD ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findAll() {
        return planRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanResponse findById(Long id) {
        return planRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));
    }

    @Override
    @Transactional
    public PlanResponse create(PlanRequest request) {
        Plan plan = new Plan();
        plan.setName(request.getName());
        plan.setTokensGranted(request.getTokensGranted());
        plan.setIsActive(true);
        return toResponse(planRepository.save(plan));
    }

    @Override
    @Transactional
    public PlanResponse update(Long id, PlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        if (planRepository.hasActiveSubscriptions(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede modificar un plan con suscripciones activas");
        }

        plan.setName(request.getName());
        plan.setTokensGranted(request.getTokensGranted());
        return toResponse(planRepository.save(plan));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!planRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado");
        }
        if (planRepository.hasActiveSubscriptions(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar un plan con suscripciones activas");
        }
        planRepository.deleteById(id);
    }

    // ── SUBSCRIPTION ─────────────────────────────────────────────────────────

    /**
     * Suscribe al usuario autenticado al plan indicado.
     *
     * Decisión de equipo sobre el saldo anterior:
     *  - Se conserva el saldo existente (tokens no vencen).
     *  - Se suman los tokens del nuevo plan al balance actual.
     *  - La suscripción anterior queda en estado INACTIVE.
     */
    @Override
    @Transactional
    public SubscriptionResponse subscribe(Long planId, String userEmail) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Desactivar suscripción activa anterior si existe
        subscriptionRepository.findByUserIdAndState(user.getId(), "ACTIVE")
                .ifPresent(existing -> {
                    existing.setState("INACTIVE");
                    subscriptionRepository.save(existing);
                });

        // Crear nueva suscripción
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusYears(1));
        subscription.setState("ACTIVE");
        subscription.setAccreditedTokens(plan.getTokensGranted());
        subscriptionRepository.save(subscription);

        // Acreditar tokens al balance del usuario (se conserva saldo previo)
        int newBalance = user.getBalanceTokens() + plan.getTokensGranted();
        user.setBalanceTokens(newBalance);
        userRepository.save(user);

        return new SubscriptionResponse(
                user.getId(),
                plan.getName(),
                plan.getTokensGranted(),
                newBalance,
                LocalDateTime.now()
        );
    }

    // ── MAPPER ───────────────────────────────────────────────────────────────

    private PlanResponse toResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getTokensGranted(),
                plan.getIsActive()
        );
    }
}
