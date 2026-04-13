package com.breaze.genesis.services;

/**
 * Service interface for token management.
 * Defines methods to consume tokens and check user balance.
 */
public interface ITokenService {

    /**
     * Consumes tokens from a user's balance.
     *
     * @param userId User identifier
     * @param cost Number of tokens to consume
     * @return true if tokens were successfully consumed, false if insufficient balance
     */
    boolean consumeTokens(Long userId, int cost);

    /**
     * Retrieves the current token balance of a user.
     *
     * @param userId User identifier
     * @return Current token balance
     */
    int getBalance(Long userId);
}