package com.breaze.genesis.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TopOperationResponse {
    private String operationCode;
    private String operationName;
    private Long   executionCount;
}
