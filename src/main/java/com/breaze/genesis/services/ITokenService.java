package com.breaze.genesis.services;

public interface ITokenService {
    boolean consumeTokens(Long userId, int cost);
    int getBalance(Long userId);
}

