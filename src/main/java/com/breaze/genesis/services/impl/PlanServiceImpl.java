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
/**
 * Implementación del servicio de planes que gestiona la creación,
 * actualización, eliminación y suscripción a planes.
 */
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements IPlanService {

    /**
     * Repositorio de planes.
     */
    private final IPlanRepository planRepository;

    /**
     * Repositorio de suscripciones.
     */
    private final ISubscriptionRepository subscriptionRepository;

    /**
     * Repositorio de usuarios.
     */
    private final IUserRepository userRepository;

    /**
     * Obtiene todos los planes disponibles.
     *
     * @return lista de planes en formato de respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> findAll() {
        return planRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene un plan por su identificador.
     *
     * @param id identificador del plan
     * @return plan encontrado en formato de respuesta
     */
    @Override
    @Transactional(readOnly = true)
    public PlanResponse findById(Long id) {
        return planRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));
    }

    /**
     * Crea un nuevo plan.
     *
     * @param request datos del plan a crear
     * @return plan creado en formato de respuesta
     */
    @Override
    @Transactional
    public PlanResponse create(PlanRequest request) {
        Plan plan = new Plan();
        plan.setName(request.getName());
        plan.setTokensGranted(request.getTokensGranted());
        plan.setIsActive(true);
        return toResponse(planRepository.save(plan));
    }

    /**
     * Actualiza un plan existente.
     *
     * @param id identificador del plan
     * @param request datos actualizados del plan
     * @return plan actualizado en formato de respuesta
     */
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

    /**
     * Elimina un plan si no tiene suscripciones activas.
     *
     * @param id identificador del plan
     */
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

    /**
     * Suscribe a un usuario a un plan.
     *
     * La lógica de negocio establece que el saldo de tokens previo del usuario
     * se conserva y se suman los tokens del nuevo plan.
     * Si el usuario tiene una suscripción activa, esta se desactiva.
     *
     * @param planId identificador del plan
     * @param userEmail correo del usuario
     * @return información de la suscripción realizada
     */
    @Override
    @Transactional
    public SubscriptionResponse subscribe(Long planId, String userEmail) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan no encontrado"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        subscriptionRepository.findByUserIdAndState(user.getId(), "ACTIVE")
                .ifPresent(existing -> {
                    existing.setState("INACTIVE");
                    subscriptionRepository.save(existing);
                });

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusYears(1));
        subscription.setState("ACTIVE");
        subscription.setAccreditedTokens(plan.getTokensGranted());
        subscriptionRepository.save(subscription);

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

    /**
     * Convierte una entidad Plan en un DTO PlanResponse.
     *
     * @param plan entidad plan
     * @return DTO con información del plan
     */
    private PlanResponse toResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getTokensGranted(),
                plan.getIsActive()
        );
    }
}