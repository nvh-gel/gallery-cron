package com.eden.cron.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for cron config.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScheduleConstants {

    public static final String EVERY_MINUTE = "0 * * * * *";
}
