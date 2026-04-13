package com.breaze.genesis.repositories;

import com.breaze.genesis.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad Transaction.
 * Permite realizar operaciones CRUD y consultas relacionadas
 * con las transacciones de los usuarios.
 */
public interface ITransactionsRepository extends JpaRepository<Transaction,Long> {

    /**
     * Obtiene la lista de transacciones asociadas a un usuario
     * a partir de su identificador.
     *
     * @param userId identificador del usuario
     * @return lista de transacciones del usuario
     */
    List<Transaction> findByUserId(Long userId);
}