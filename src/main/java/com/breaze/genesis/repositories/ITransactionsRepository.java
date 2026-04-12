package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionsRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long userId);
}
