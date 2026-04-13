package com.breaze.genesis.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class TransactionResponse {
    private Long          id;
    private String        operation;
    private Integer       tokensUsed;
    private LocalDateTime date;
}
