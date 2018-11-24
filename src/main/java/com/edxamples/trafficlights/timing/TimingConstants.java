package com.edxamples.trafficlights.timing;

/**
 * As a design , i have decided that this transition timing should be constant,
 * because in a real world scenario this ARE constant,
 * the green ones vary based on the time of day not these ones
 */
public class TimingConstants {

    static final long AMBER_MAJOR_TO_RED_AFTER_MAJOR_MILLIS = 5000;
    static final long RED_AFTER_MAJOR_TO_GREEN_MINOR_MILLIS = 5000;
    static final long AMBER_MINOR_TO_RED_AFTER_MINOR_MILLIS = 5000;
    static final long RED_AFTER_MINOR_TO_GREEN_MAJOR_MILLIS = 5000;
    static final long TRAFFIC_EVENT_FREQUENCY_MILLIS = 100000;
}

