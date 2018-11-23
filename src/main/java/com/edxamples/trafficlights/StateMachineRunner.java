package com.edxamples.trafficlights;

import com.edxamples.trafficlights.shared.Events;
import com.edxamples.trafficlights.shared.States;
import com.edxamples.trafficlights.shared.Timings;
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
    Timings timings;

    @Autowired
    StateMachine<States, Events> stateMachine;

    /**
     * Set up continuous loop for running the state machine
     */
    void run(boolean infinite) throws InterruptedException {
        log.info("Init State: " + stateMachine.getInitialState().getId());
        do {
            triggerTraffic(TimeUnit.MILLISECONDS, timings.getTrafficEventMillis());
        } while (infinite);
    }

    /**
     * Steps for the state machine
     * it should wait, and also change based on the north-south boolean
     */
    void triggerTraffic(TimeUnit unit, int amount) throws InterruptedException {
        unit.sleep(amount);
        log.info("Traffic arrives at minor road (Event transition Fired)");
        stateMachine.sendEvent(Events.MINOR_ROAD_TRAFFIC);
    }

    /**
     * @return helper method for getting the state
     */
    States getState(){
        return stateMachine.getState().getId();
    }
}
