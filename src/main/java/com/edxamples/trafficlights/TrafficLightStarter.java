package com.edxamples.trafficlights;

import com.edxamples.trafficlights.statemachine.TrafficLightEvents;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

/**
 * Class used for calling trigger evnet to send so that the traffic light cycle will begin
 */
@Component
public class TrafficLightStarter {

    @Autowired
    StateMachine<TrafficLightStates, TrafficLightEvents> stateMachine;

    /**
     * Set up continuous loop for running the state machine
     */
    void run() {
       stateMachine.sendEvent(TrafficLightEvents.TURN_ON);
    }

}
