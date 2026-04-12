package com.breaze.genesis.dtos.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class SubscriptionResponse {
    private Long          userId;
    private String        planName;
    private Integer       tokensGranted;
    private Integer       newBalance;
    private LocalDateTime subscribedAt;
}
