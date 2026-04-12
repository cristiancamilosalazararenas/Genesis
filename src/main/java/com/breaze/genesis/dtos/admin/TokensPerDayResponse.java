package com.breaze.genesis.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class TokensPerDayResponse {
    private LocalDate date;
    private Integer   tokensConsumed;
}