package com.edxamples.trafficlights.shared;

import org.springframework.stereotype.Component;

/**
 * Timings for each transition (bar the green to amber major due to that being triggered by an event)
 * And also the timing for the traffic event frequency, all timings should be milliseconds
 */
@Component
public class Timings {

    private int trafficEventMillis;
    private int amberMajorToRedMajorMillis;
    private int redMajorToGreenMinorMillis;
    private int greenMinorToAmberMinorMillis;
    private int amberMinorToRedMinorMillis;
    private int redMinorToGreenMajorMillis;

    public Timings(int trafficEventMillis, int amberMajorToRedMajorMillis, int redMajorToGreenMinor, int greenMinorToAmberMinor, int amberMinorToRedMinor, int redMinorToGreenMajor) {
        this.trafficEventMillis = trafficEventMillis;
        this.amberMajorToRedMajorMillis = amberMajorToRedMajorMillis;
        this.redMajorToGreenMinorMillis = redMajorToGreenMinor;
        this.greenMinorToAmberMinorMillis = greenMinorToAmberMinor;
        this.amberMinorToRedMinorMillis = amberMinorToRedMinor;
        this.redMinorToGreenMajorMillis = redMinorToGreenMajor;
    }

    public static Timings buildTimingsAllSameForLights(int trafficEventMillis, int amount){
        return new Timings(trafficEventMillis,amount,amount,amount,amount,amount);
    }

    public int getAmberMajorToRedMajorMillis() {
        return amberMajorToRedMajorMillis;
    }

    public int getRedMajorToGreenMinorMillis() {
        return redMajorToGreenMinorMillis;
    }

    public int getGreenMinorToAmberMinorMillis() {
        return greenMinorToAmberMinorMillis;
    }

    public int getAmberMinorToRedMinorMillis() {
        return amberMinorToRedMinorMillis;
    }

    public int getRedMinorToGreenMajorMillis() {
        return redMinorToGreenMajorMillis;
    }

    public int getTrafficEventMillis() {
        return trafficEventMillis;
    }

}
