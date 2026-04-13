package com.breaze.genesis.services.impl;

import com.breaze.genesis.dtos.operation.*;
import com.breaze.genesis.services.IOperationService;
import com.breaze.genesis.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements IOperationService {

    @Autowired
    private ITokenService tokenService;

    @Override
    public CreditResponse calculateCredit(CreditRequest request, Long userId) {
        int baseCost = 50;
        if (!tokenService.consumeTokens(userId, baseCost)) {
            throw new RuntimeException("Insufficient tokens");
        }
        double i = request.getMonthlyRate() / 100;
        double payment = request.getPrice() * (i * Math.pow(1+i, request.getInstallments())) / (Math.pow(1+i, request.getInstallments()) - 1);
        double totalPaid = payment * request.getInstallments();
        double totalInterest = totalPaid - request.getPrice();

        CreditResponse response = new CreditResponse();
        response.setMonthlyPayment(payment);
        response.setTotalPaid(totalPaid);
        response.setTotalInterest(totalInterest);
        return response;
    }

    @Override
    public ConversionResponse convertCurrency(ConversionRequest request, Long userId) {
        int baseCost = 20;
        if (!tokenService.consumeTokens(userId, baseCost)) {
            throw new RuntimeException("Insufficient tokens");
        }
        double rate = 4000; // Example, should come from DB
        double result = request.getSourceCurrency().equals("COP") ?
                request.getAmount() / rate : request.getAmount() * rate;

        ConversionResponse response = new ConversionResponse();
        response.setResult(result);
        response.setDirection(request.getSourceCurrency().equals("COP") ? "COP->USD" : "USD->COP");
        response.setAppliedRate(rate);
        response.setLastUpdate(java.time.LocalDateTime.now());
        return response;
    }

    @Override
    public ImcResponse calculateBmi(ImcRequest request, Long userId) {
        int baseCost = 15;
        if (!tokenService.consumeTokens(userId, baseCost)) {
            throw new RuntimeException("Insufficient tokens");
        }
        double heightM = request.getHeightCm() / 100;
        double bmi = request.getWeightKg() / (heightM * heightM);

        String category;
        if (bmi < 18.5) category = "Underweight";
        else if (bmi < 25.0) category = "Normal weight";
        else if (bmi < 30.0) category = "Overweight";
        else category = "Obesity";

        double minWeight = 18.5 * heightM * heightM;
        double maxWeight = 24.9 * heightM * heightM;

        ImcResponse response = new ImcResponse();
        response.setBmi(Math.round(bmi * 100.0) / 100.0);
        response.setCategory(category);
        response.setMinHealthyWeight(minWeight);
        response.setMaxHealthyWeight(maxWeight);
        response.setWeightDifference(request.getWeightKg() - maxWeight);
        return response;
    }

    @Override
    public SleepResponse calculateSleep(SleepRequest request, Long userId) {
        int baseCost = 20;
        if (!tokenService.consumeTokens(userId, baseCost)) {
            throw new RuntimeException("Insufficient tokens");
        }
        SleepResponse response = new SleepResponse();
        response.setCalculatedTime("07:30"); // Example
        response.setTotalHours(7.5);
        response.setQuality("Ideal");
        return response;
    }
}
