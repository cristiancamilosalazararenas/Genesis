package com.breaze.genesis.services;

import com.breaze.genesis.dtos.plan.PlanRequest;
import com.breaze.genesis.dtos.plan.PlanResponse;
import com.breaze.genesis.dtos.plan.SubscriptionResponse;

import java.util.List;

public interface IPlanService {

    List<PlanResponse> findAll();

    PlanResponse findById(Long id);

    PlanResponse create(PlanRequest request);

    PlanResponse update(Long id, PlanRequest request);

    void delete(Long id);

    SubscriptionResponse subscribe(Long planId, String userEmail);
}
