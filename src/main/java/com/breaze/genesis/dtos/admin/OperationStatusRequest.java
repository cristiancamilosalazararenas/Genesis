package com.breaze.genesis.dtos.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OperationStatusRequest {

    @NotNull(message = "El campo active es obligatorio")
    private Boolean active;
}