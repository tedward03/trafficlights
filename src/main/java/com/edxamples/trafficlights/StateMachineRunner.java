package com.edxamples.trafficlights;

import com.edxamples.trafficlights.shared.Events;
import com.edxamples.trafficlights.shared.States;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Class used for calling the steps that the statemachine should take in order
 */
@Component
public class StateMachineRunner {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    /**
     * used to dictate which events should be sent at what point toggled when there is a new redall state triggered
     */
    private boolean northSouthTimeToGo  = false;

    @Autowired
    StateMachine<States, Events> stateMachine;

    /**
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        log.info("Init State: " + stateMachine.getInitialState().getId());
        while(true) {
            stepThrough();
        }
    }

    /**
     * Steps for the state machine
     * it should wait, and also change based on the north-south boolean
     */
    void stepThrough() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        if (northSouthTimeToGo) {
            stateMachine.sendEvent(Events.LIGHTCHANGE_NS);
            if (stateMachine.getState().getId() == States.REDALL) {
                northSouthTimeToGo = !northSouthTimeToGo;
            }
        } else {
            stateMachine.sendEvent(Events.LIGHTCHANGE_EW);
            if (stateMachine.getState().getId() == States.REDALL) {
                northSouthTimeToGo = !northSouthTimeToGo;
            }
        }
    }

    /**
     * @return boolean for which set of traffic lights is on sequence
     */
    boolean isNorthSouthTimeToGo() {
        return northSouthTimeToGo;
    }

    /**
     * @return helper method for getting the state
     */
    States getState(){
        return stateMachine.getState().getId();
    }
}
