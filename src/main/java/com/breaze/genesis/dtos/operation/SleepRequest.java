package com.breaze.genesis.dtos.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Request DTO for sleep calculation.
 * Contains mode, reference time and minutes to fall asleep.
 */
@Getter
@Setter
@NoArgsConstructor
public class SleepRequest {
    /** Mode (WAKE_UP or BED_TIME) */
    private String mode;

    /** Reference time in HH:mm format */
    private String time;

    /** Minutes to fall asleep (default 14) */
    private int minutesToFallAsleep = 14;
}
