package com.edxamples.trafficlights.timing.services;


import com.edxamples.trafficlights.Application;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

/**
 * Used to handle the input from the command line arguments , or if they fail at validation, setting the args to be noww
 */
@Component
public class TimingInputHandler {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    private Time time;
    private String dayCode;

    private final static String dayCodeRegex = "MON|TUE|WED|THU|FRI|SAT|SUN";
    private final static String timeRegex = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";


    public TimingInputHandler(String time, String dayCode) {
        this.time = handleTimeInput(time);
        this.dayCode = handleDayCodeInput(dayCode);
    }

    /**
     * The input must be checked on startup to see that it matches the time that we need
     * @param time the string input from the command line args
     * @return the time either now or from the input but checked which is the important thing
     */
    private Time handleTimeInput(String time){
        if(!time.matches(timeRegex)){
            LocalTime now  = LocalTime.now();
            Time nowTime = Time.valueOf(now);
            log.warn("The time specified in args didn't match the format we needed (HH:mm) so defaulting to right now : "+ nowTime);
            return nowTime;
        }
        return Time.valueOf(time);
    }

    /**
     * Ihe input must adhere to the code regex that I specified , otherwise it will just find the current day and use that
     * @param dayCode the code that came in from the args must be three letters uppercase dictating the day
     * @return the same as the input or the formatted version from right now
     */
    private String handleDayCodeInput(String dayCode){
        if(!dayCode.matches(dayCodeRegex)){
            String nowDayCode = new SimpleDateFormat("E").format(new Date());
            log.warn("The day specified in args didn't match the format we needed (MON/TUE/etc) so defaulting to right now : "+ nowDayCode);
            return nowDayCode.toUpperCase();
        }
        return dayCode;
    }

    public Time getTime() {
        return time;
    }

    public String getDayCode() {
        return dayCode;
    }
}
