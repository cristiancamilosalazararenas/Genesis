package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol, Long> {
}
