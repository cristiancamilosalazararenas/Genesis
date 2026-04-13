package com.breaze.genesis.dtos.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PlanResponse {
    private Long    id;
    private String  name;
    private Integer tokensGranted;
    private Boolean active;
}
