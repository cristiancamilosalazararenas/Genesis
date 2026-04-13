package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    // Usamos findById(1L) para recuperar el registro singleton de tasa de cambio
}
