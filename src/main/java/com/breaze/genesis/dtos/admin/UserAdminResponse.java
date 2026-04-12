package com.breaze.genesis.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserAdminResponse {
    private Long    id;
    private String  email;
    private Integer tokens;
    private String  plan;
    private String  status;
}