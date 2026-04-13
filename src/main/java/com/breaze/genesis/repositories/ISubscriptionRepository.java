package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndState(Long userId, String state);

    boolean existsByUserIdAndState(Long userId, String state);
}
