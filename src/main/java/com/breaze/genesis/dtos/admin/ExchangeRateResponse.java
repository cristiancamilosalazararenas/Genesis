package com.breaze.genesis.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExchangeRateResponse {
    private BigDecimal    copPerUsd;
    private LocalDateTime lastUpdated;
}