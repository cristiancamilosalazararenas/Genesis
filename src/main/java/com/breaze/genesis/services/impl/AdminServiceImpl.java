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

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final IUserRepository         userRepository;
    private final IOperationRepository    operationRepository;
    private final IExchangeRateRepository exchangeRateRepository;
    private final ITransactionRepository  transactionRepository;
    private final ISubscriptionRepository subscriptionRepository;

    // ── USERS ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<UserAdminResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::toUserAdminResponse);
    }

    @Override
    @Transactional
    public AdminMessageResponse updateUserStatus(Long userId, UserStatusRequest request) {
        User user = findUserOrThrow(userId);
        user.setStatus(request.getStatus());
        userRepository.save(user);
        return new AdminMessageResponse("Estado del usuario actualizado a " + request.getStatus());
    }

    @Override
    @Transactional
    public TokenRechargeResponse rechargeTokens(Long userId, TokenRechargeRequest request) {
        User user = findUserOrThrow(userId);
        int newBalance = user.getBalanceTokens() + request.getAmount();
        user.setBalanceTokens(newBalance);
        userRepository.save(user);
        return new TokenRechargeResponse(userId, request.getAmount(), newBalance);
    }

    // ── OPERATIONS ────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public AdminMessageResponse updateOperationStatus(String code, OperationStatusRequest request) {
        Operation operation = operationRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Operación no encontrada: " + code));
        operation.setActive(request.getActive());
        operationRepository.save(operation);
        return new AdminMessageResponse("Operación " + code + " actualizada a active=" + request.getActive());
    }

    // ── EXCHANGE RATE ─────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public ExchangeRateResponse getExchangeRate() {
        ExchangeRate rate = exchangeRateRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tasa de cambio no configurada"));
        return new ExchangeRateResponse(rate.getCopPerUsd(), rate.getLastUpdated());
    }

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

    // ── METRICS ───────────────────────────────────────────────────────────────

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

    // ── HELPERS ───────────────────────────────────────────────────────────────

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + userId));
    }

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