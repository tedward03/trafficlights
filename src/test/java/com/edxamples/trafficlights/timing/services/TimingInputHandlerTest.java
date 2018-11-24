package com.edxamples.trafficlights.timing.services;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;

import static org.hamcrest.core.Is.is;

public class TimingInputHandlerTest {

    @Test
    public void testRightInput() throws Exception {
        TimingInputHandler handler = new TimingInputHandler("09:00:00","TUE");
        Assert.assertThat(handler.getDayCode(),is("TUE"));
        Assert.assertThat(handler.getTime(),is(Time.valueOf("09:00:00")));
    }

    @Test
    public void testWrongInput() throws Exception {
        String wrongDayCodeInput =  "NOT_A_DAY_OF_WEEK_CODE";
        String wrongTimeInput = "54:34";
        TimingInputHandler handler = new TimingInputHandler(wrongTimeInput,wrongDayCodeInput);
        Assert.assertNotEquals(wrongDayCodeInput,handler.getDayCode().length());
        Assert.assertNotEquals(wrongTimeInput,handler.getTime());
    }

}