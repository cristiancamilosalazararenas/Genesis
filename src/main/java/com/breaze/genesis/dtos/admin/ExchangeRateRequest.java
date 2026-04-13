package com.breaze.genesis.dtos.admin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExchangeRateRequest {

    @NotNull(message = "La tasa COP/USD es obligatoria")
    @DecimalMin(value = "0.01", message = "La tasa debe ser mayor a 0")
    private BigDecimal copPerUsd;
}
