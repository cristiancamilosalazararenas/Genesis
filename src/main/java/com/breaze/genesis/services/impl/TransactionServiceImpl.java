package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.transaction.TransactionResponse;
import com.breaze.genesis.entities.Transaction;
import com.breaze.genesis.entities.User;
import com.breaze.genesis.repositories.ITransactionRepository;
import com.breaze.genesis.repositories.IUserRepository;
import com.breaze.genesis.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final IUserRepository        userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getHistory(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return transactionRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    // ── MAPPER ───────────────────────────────────────────────────────────────

    private TransactionResponse toResponse(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getOperation().getName(),
                tx.getTokensConsumed(),
                tx.getDate()
        );
    }
}
