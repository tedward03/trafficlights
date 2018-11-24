package com.edxamples.trafficlights.statemachine;


import com.edxamples.trafficlights.Application;
import com.edxamples.trafficlights.timing.TimingFacade;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * USed to send a log for each state transition, also uses the timings facade to get the duration for each state
 */
@Component
public class TrafficLightListener extends StateMachineListenerAdapter<TrafficLightStates,TrafficLightEvents> {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    Map<TrafficLightStates,TrafficLightStates> transitionsHistory = new HashMap<>();

    @Autowired
    TimingFacade timingFacade;

    @Autowired
    TimingInputHandler timingInputHandler;

    @Override
    public void stateChanged(State<TrafficLightStates,TrafficLightEvents> from, State<TrafficLightStates,TrafficLightEvents> to) {
        //startup states
        if(from == null && to.getId() == TrafficLightStates.OFF){
            log.info("time was set to " + timingInputHandler.getTime() + " and Day was set to " + timingInputHandler.getDayCode());
            return;
        }
        log.info("State change: " + from.getId() +" -> " + to.getId()  + "\t\t " +to.getId() + " Duration: (" + timingFacade.getTimeFor(to.getId())/1000 + "s)");
        recordTransitions(from.getId(),to.getId());
    }

    private void recordTransitions(TrafficLightStates from, TrafficLightStates to){
        if(!transitionsHistory.containsKey(from)){
            transitionsHistory.put(from, to);
        }
    }

    public Map<TrafficLightStates,TrafficLightStates> getTransitionsHistory(){
        return transitionsHistory;
    }

}