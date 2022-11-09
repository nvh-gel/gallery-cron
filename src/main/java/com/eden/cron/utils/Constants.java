package com.eden.cron.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants storing class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String SENT_MESSAGE = "sent message: {} to topic: {}";
    public static final String RECEIVED_MESSAGE = "message received: {}";
    public static final String PROCESSED_MESSAGE = "processed {} message: {}";
}
