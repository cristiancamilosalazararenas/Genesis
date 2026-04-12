package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanRepository extends JpaRepository<Plan,Long> {
}
