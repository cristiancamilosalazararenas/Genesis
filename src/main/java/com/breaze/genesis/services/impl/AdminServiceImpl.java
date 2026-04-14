package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.admin.*;
import com.breaze.genesis.entities.ExchangeRate;
import com.breaze.genesis.entities.Operation;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.*;
import com.breaze.genesis.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Implementación del servicio de administración que gestiona usuarios,
 * operaciones, tasas de cambio y métricas del sistema.
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    /**
     * Repositorio de usuarios.
     */
    private final IUserRepository userRepository;

    /**
     * Repositorio de operaciones.
     */
    private final IOperationRepository operationRepository;

    /**
     * Repositorio de tasas de cambio.
     */
    private final IExchangeRateRepository exchangeRateRepository;

    /**
     * Repositorio de transacciones.
     */
    private final ITransactionRepository transactionRepository;

    /**
     * Repositorio de suscripciones.
     */
    private final ISubscriptionRepository subscriptionRepository;

    /**
     * Obtiene todos los usuarios de forma paginada.
     *
     * @param pageable configuración de paginación
     * @return página de usuarios en formato administrativo
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAdminResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::toUserAdminResponse);
    }

    /**
     * Actualiza el estado de un usuario.
     *
     * @param userId identificador del usuario
     * @param request datos con el nuevo estado
     * @return mensaje de confirmación
     */
    @Override
    @Transactional
    public AdminMessageResponse updateUserStatus(Long userId, UserStatusRequest request) {
        User user = findUserOrThrow(userId);
        user.setStatus(request.getStatus());
        userRepository.save(user);
        return new AdminMessageResponse("Estado del usuario actualizado a " + request.getStatus());
    }

    /**
     * Recarga tokens a un usuario.
     *
     * @param userId identificador del usuario
     * @param request datos de la recarga
     * @return información de la recarga realizada
     */
    @Override
    @Transactional
    public TokenRechargeResponse rechargeTokens(Long userId, TokenRechargeRequest request) {
        User user = findUserOrThrow(userId);
        int newBalance = user.getBalanceTokens() + request.getAmount();
        user.setBalanceTokens(newBalance);
        userRepository.save(user);
        return new TokenRechargeResponse(userId, request.getAmount(), newBalance);
    }

    /**
     * Actualiza el estado de una operación.
     *
     * @param code identificador de la operación
     * @param request datos con el nuevo estado
     * @return mensaje de confirmación
     */
    @Override
    @Transactional
    public AdminMessageResponse updateOperationStatus(String code, OperationStatusRequest request) {
        Operation operation = operationRepository.findById(Long.valueOf(code))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Operación no encontrada: " + code));
        operation.setActive(request.getActive());
        operationRepository.save(operation);
        return new AdminMessageResponse("Operación " + code + " actualizada a active=" + request.getActive());
    }

    /**
     * Obtiene la tasa de cambio actual.
     *
     * @return información de la tasa de cambio
     */
    @Override
    @Transactional(readOnly = true)
    public ExchangeRateResponse getExchangeRate() {
        ExchangeRate rate = exchangeRateRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tasa de cambio no configurada"));
        return new ExchangeRateResponse(rate.getCopPerUsd(), rate.getLastUpdated());
    }

    /**
     * Actualiza la tasa de cambio.
     *
     * @param request datos de la nueva tasa
     * @return información actualizada de la tasa de cambio
     */
    @Override
    @Transactional
    public ExchangeRateResponse updateExchangeRate(ExchangeRateRequest request) {
        ExchangeRate rate = exchangeRateRepository.findById(1L)
                .orElse(new ExchangeRate());
        rate.setId(1L);
        rate.setCopPerUsd(request.getCopPerUsd());
        rate.setLastUpdated(LocalDateTime.now());
        exchangeRateRepository.save(rate);
        return new ExchangeRateResponse(rate.getCopPerUsd(), rate.getLastUpdated());
    }

    /**
     * Obtiene la cantidad de tokens usados por día en un rango de fechas.
     *
     * @param from fecha inicial
     * @param to fecha final
     * @return lista de tokens agrupados por día
     */
    @Override
    @Transactional(readOnly = true)
    public List<TokensPerDayResponse> getTokensPerDay(LocalDate from, LocalDate to) {
        LocalDate effectiveFrom = (from != null) ? from : LocalDate.now().minusDays(30);
        LocalDate effectiveTo   = (to   != null) ? to   : LocalDate.now();

        return transactionRepository.findTokensPerDay(effectiveFrom, effectiveTo)
                .stream()
                .map(row -> new TokensPerDayResponse(
                        LocalDate.parse(row[0].toString()),
                        ((Number) row[1]).intValue()
                ))
                .toList();
    }

    /**
     * Obtiene las operaciones más utilizadas.
     *
     * @param pageable configuración de paginación
     * @return página con las operaciones más frecuentes
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopOperationResponse> getTopOperations(Pageable pageable) {
        return transactionRepository.findTopOperations(pageable)
                .map(row -> new TopOperationResponse(
                        row[0].toString(),
                        row[1].toString(),
                        ((Number) row[2]).longValue()
                ));
    }

    /**
     * Obtiene los usuarios con mayor consumo de tokens.
     *
     * @param pageable configuración de paginación
     * @return página con los usuarios más activos
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TopUserResponse> getTopUsers(Pageable pageable) {
        return transactionRepository.findTopUsers(pageable)
                .map(row -> new TopUserResponse(
                        ((Number) row[0]).longValue(),
                        row[1].toString(),
                        ((Number) row[2]).longValue()
                ));
    }

    /**
     * Busca un usuario por su identificador o lanza una excepción si no existe.
     *
     * @param userId identificador del usuario
     * @return usuario encontrado
     */
    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + userId));
    }

    /**
     * Convierte una entidad User en un DTO UserAdminResponse.
     *
     * @param user entidad usuario
     * @return DTO con información administrativa del usuario
     */
    private UserAdminResponse toUserAdminResponse(User user) {
        String activePlan = subscriptionRepository
                .findByUserIdAndState(user.getId(), "ACTIVE")
                .map(s -> s.getPlan().getName())
                .orElse("NINGUNO");

        return new UserAdminResponse(
                user.getId(),
                user.getEmail(),
                user.getBalanceTokens(),
                activePlan,
                user.getStatus()
        );
    }
}