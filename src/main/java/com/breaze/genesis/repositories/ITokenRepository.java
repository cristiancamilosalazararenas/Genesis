package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserId(Long userId);
}

