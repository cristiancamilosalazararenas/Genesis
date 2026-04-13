package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SleepRequest {
    private int bedtime;
    private int wakeup;
}

