package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    /** Historial paginado del usuario autenticado */
    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    /** Tokens consumidos agrupados por día (rango configurable) */
    @Query(value =
            "SELECT DATE(t.date) AS day, SUM(t.tokens_consumed) AS total " +
                    "FROM transactions t " +
                    "WHERE DATE(t.date) BETWEEN :from AND :to " +
                    "GROUP BY DATE(t.date) " +
                    "ORDER BY DATE(t.date)",
            nativeQuery = true)
    List<Object[]> findTokensPerDay(
            @Param("from") LocalDate from,
            @Param("to")   LocalDate to);

    /** Operaciones ordenadas por cantidad de ejecuciones */
    @Query("SELECT t.operation.code, t.operation.name, COUNT(t) AS cnt " +
            "FROM Transaction t " +
            "GROUP BY t.operation.code, t.operation.name " +
            "ORDER BY cnt DESC")
    Page<Object[]> findTopOperations(Pageable pageable);

    /** Usuarios ordenados por tokens totales consumidos */
    @Query("SELECT t.user.id, t.user.email, SUM(t.tokensConsumed) AS total " +
            "FROM Transaction t " +
            "GROUP BY t.user.id, t.user.email " +
            "ORDER BY total DESC")
    Page<Object[]> findTopUsers(Pageable pageable);
}
