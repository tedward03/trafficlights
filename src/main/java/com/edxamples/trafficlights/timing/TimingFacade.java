package com.edxamples.trafficlights.timing;

import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.database.Timings;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.edxamples.trafficlights.statemachine.TrafficLightStates.*;

/**
 * Centralized place for all of the timings to be take from, whether this be from constants or they come from the database
 * ON BOOT the map should be filled so that the TrafficLightsStatConfig can set up the timers
 */
@Component
public class TimingFacade {

    private Map<TrafficLightStates,Long> stateConstantTimingsMap = new HashMap<>();

    @Autowired
    TimingsRepository timingsRepository;

    @Autowired
    TimingInputHandler handler;

    /**
     * Fill the green times after construction
     */
    @PostConstruct
    public void setup(){
        stateConstantTimingsMap.put(AMBER_MAJOR,TimingConstants.AMBER_MAJOR_TO_RED_AFTER_MAJOR_MILLIS);
        stateConstantTimingsMap.put(AMBER_MINOR,TimingConstants.AMBER_MINOR_TO_RED_AFTER_MINOR_MILLIS);
        stateConstantTimingsMap.put(RED_MAJOR,TimingConstants.RED_AFTER_MAJOR_TO_GREEN_MINOR_MILLIS);
        stateConstantTimingsMap.put(RED_MINOR,TimingConstants.RED_AFTER_MINOR_TO_GREEN_MAJOR_MILLIS);
        Timings timings = timingsRepository.findByTimeAndDay(handler.getTime(),handler.getDayCode());
        stateConstantTimingsMap.put(GREEN_MINOR,timings.getGreenMinorDurationMillis());
        stateConstantTimingsMap.put(GREEN_MAJOR,timings.getGreenMajorDurationMillis());
    }

    public Long getTimeFor(TrafficLightStates state){
        return stateConstantTimingsMap.get(state);
    }

}
