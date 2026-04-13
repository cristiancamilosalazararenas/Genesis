package com.breaze.genesis.controllers;

import com.breaze.genesis.dtos.transaction.TransactionResponse;
import com.breaze.genesis.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    /** Historial de transacciones del usuario autenticado (paginado) */
    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getHistory(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        return ResponseEntity.ok(
                transactionService.getHistory(
                        authentication.getName(),
                        PageRequest.of(page, size)
                )
        );
    }
}
