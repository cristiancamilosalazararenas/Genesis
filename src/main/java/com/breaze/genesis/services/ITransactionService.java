package com.breaze.genesis.services;

import com.breaze.genesis.dtos.transaction.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransactionService {

    Page<TransactionResponse> getHistory(String userEmail, Pageable pageable);
}
