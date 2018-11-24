package com.edxamples.trafficlights.timing;

import com.edxamples.trafficlights.statemachine.States;
import com.edxamples.trafficlights.timing.database.Timings;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TimingsFacade {

    private Map<States,Object> stateConstantTimingsMap = new HashMap<>();

    @Autowired
    TimingsRepository timingsRepository;

    @Autowired
    TimingInputHandler handler;

    // used to find out the duration that green major has
    private long greenMajorStartTime;


    interface GreenCommand {
        Long getGreen();
    }

    /**
     * Fill the green times after construction
     */
    @PostConstruct
    public void setup(){
        stateConstantTimingsMap.put(States.AMBER_MAJOR,TimingConstants.AMBER_MAJOR_TO_RED_AFTER_MAJOR_MILLIS);
        stateConstantTimingsMap.put(States.AMBER_MINOR,TimingConstants.AMBER_MINOR_TO_RED_AFTER_MINOR_MILLIS);
        stateConstantTimingsMap.put(States.RED_MAJOR,TimingConstants.RED_AFTER_MAJOR_TO_GREEN_MINOR_MILLIS);
        stateConstantTimingsMap.put(States.RED_MINOR,TimingConstants.RED_AFTER_MINOR_TO_GREEN_MAJOR_MILLIS);
        stateConstantTimingsMap.put(States.GREEN_MAJOR_FINISHING,new GreenCommand(){
            @Override
            public Long getGreen() {
                return getGreenMajorDurationLeft();
            }
        });
        Timings timings = timingsRepository.findByTimeAndDay(handler.getTime(),handler.getDayCode());
        stateConstantTimingsMap.put(States.GREEN_MINOR,timings.getGreenMinorDurationMillis());
        stateConstantTimingsMap.put(States.GREEN_MAJOR_NO_EVENT_YET,timings.getGreenMajorDurationMillis());
    }


    public Long getTimeFor(States state){
        Object obj = (stateConstantTimingsMap.get(state));
        if(obj instanceof GreenCommand){
            return ((GreenCommand) obj).getGreen();
        }
        return (Long) stateConstantTimingsMap.get(state);
    }
    /**
     * Due to the way that the duration of green should work
     * i think that the time for green to continue to be on after
     * the event has been fired should be the time that green has been on
     * plus the amount of time left on its duration
     * @return duration left
     */
    private Long getGreenMajorDurationLeft () {
        Long greenMajorTimeMillis = (Long) stateConstantTimingsMap.get(States.GREEN_MAJOR_NO_EVENT_YET);
        long now = new DateTime().getMillis();
        long currentDurationOfGreen = now - this.greenMajorStartTime;
        if(currentDurationOfGreen >= greenMajorTimeMillis){
            // no more time left green has been on for too long
            return 0L;
        }else {
            // get the left over from the green major time minus the time that has already executed
            return greenMajorTimeMillis - currentDurationOfGreen;
        }
    }

    public long getGreenMajorStartTime() {
        return greenMajorStartTime;
    }

    public void setGreenMajorStartTime(long greenMajorStartTime) {
        this.greenMajorStartTime = greenMajorStartTime;
    }

    public long getTrafficEventFrequencyTime(){
        return TimingConstants.TRAFFIC_EVENT_FREQUENCY_MILLIS;
    }
}
