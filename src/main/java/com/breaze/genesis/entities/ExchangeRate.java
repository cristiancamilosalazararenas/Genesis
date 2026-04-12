package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

    @Id
    @Column(name = "id")
    private Long id;  // siempre usaremos id=1 (singleton)

    @Column(name = "cop_per_usd", nullable = false, precision = 12, scale = 2)
    private BigDecimal copPerUsd;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}
