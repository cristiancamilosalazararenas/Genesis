package com.breaze.genesis.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TokenRechargeResponse {
    private Long    userId;
    private Integer tokensAdded;
    private Integer newBalance;
}
