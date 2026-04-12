package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOperation extends JpaRepository<Operation, Long> {
}
