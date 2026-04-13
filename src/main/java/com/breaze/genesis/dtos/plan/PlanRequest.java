package com.breaze.genesis.dtos.plan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PlanRequest {

    @NotBlank(message = "El nombre del plan es obligatorio")
    private String name;

    @NotNull(message = "Los tokens otorgados son obligatorios")
    @Min(value = 1, message = "Debe otorgar al menos 1 token")
    private Integer tokensGranted;
}
