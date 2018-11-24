package com.edxamples.trafficlights;

import com.edxamples.trafficlights.timing.TimingsFacade;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import com.edxamples.trafficlights.statemachine.Events;
import com.edxamples.trafficlights.statemachine.States;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Class used for calling trigger evnet to send so that the traffic light cycle will begin
 */
@Component
public class StateMachineRunner {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    @Autowired
    TimingsFacade timingsFacade;

    @Autowired
    StateMachine<States, Events> stateMachine;

    /**
     * Set up continuous loop for running the state machine
     */
    void run(boolean infinite) throws InterruptedException {
        log.info("Init State: " + stateMachine.getInitialState().getId());
        do {
            triggerTraffic(TimeUnit.MILLISECONDS, timingsFacade.getTrafficEventFrequencyTime());
        } while (infinite);
    }

    /**
     * Steps for the state machine
     * it should wait, and also change based on the north-south boolean
     */
    void triggerTraffic(TimeUnit unit, long amount) throws InterruptedException {
        log.info("Traffic arrives at minor road (Event transition Fired)");
        stateMachine.sendEvent(Events.MINOR_ROAD_TRAFFIC);
        unit.sleep(amount);
    }

    /**
     * @return helper method for getting the state
     */
    States getState(){
        return stateMachine.getState().getId();
    }
}
