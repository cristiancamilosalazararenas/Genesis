package com.breaze.genesis.services.impl;

import com.breaze.genesis.entities.Token;
import com.breaze.genesis.repositories.ITokenRepository;
import com.breaze.genesis.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private ITokenRepository tokenRepository;

    @Override
    public boolean consumeTokens(Long userId, int cost) {
        Token token = tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (token.getBalance() < cost) {
            return false;
        }
        token.setBalance(token.getBalance() - cost);
        tokenRepository.save(token);
        return true;
    }

    @Override
    public int getBalance(Long userId) {
        return tokenRepository.findByUserId(userId)
                .map(Token::getBalance)
                .orElse(0);
    }
}

