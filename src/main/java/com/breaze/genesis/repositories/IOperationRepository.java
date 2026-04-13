package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, String> {
}
