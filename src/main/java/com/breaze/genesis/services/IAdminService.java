package com.breaze.genesis.services;

import com.breaze.genesis.dtos.admin.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IAdminService {

    // Users
    Page<UserAdminResponse> getAllUsers(Pageable pageable);
    AdminMessageResponse updateUserStatus(Long userId, UserStatusRequest request);
    TokenRechargeResponse rechargeTokens(Long userId, TokenRechargeRequest request);

    // Operations
    AdminMessageResponse updateOperationStatus(String code, OperationStatusRequest request);

    // Exchange rate
    ExchangeRateResponse getExchangeRate();
    ExchangeRateResponse updateExchangeRate(ExchangeRateRequest request);

    // Metrics
    List<TokensPerDayResponse> getTokensPerDay(LocalDate from, LocalDate to);
    Page<TopOperationResponse> getTopOperations(Pageable pageable);
    Page<TopUserResponse> getTopUsers(Pageable pageable);
}