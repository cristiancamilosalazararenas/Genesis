package com.breaze.genesis.services;

import com.breaze.genesis.dtos.operation.*;

public interface IOperationService {
    CreditResponse calculateCredit(CreditRequest request, Long userId);
    ConversionResponse convertCurrency(ConversionRequest request, Long userId);
    ImcResponse calculateBmi(ImcRequest request, Long userId);
    SleepResponse calculateSleep(SleepRequest request, Long userId);
}

