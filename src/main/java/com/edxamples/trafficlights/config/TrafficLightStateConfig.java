package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.statemachine.TrafficLightEvents;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.TimingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.EnumSet;

import static com.edxamples.trafficlights.statemachine.TrafficLightStates.*;

/**
 * Configuration for the states of the traffic lights,
 * this config holds tne stats and their transitions
 * the starting state
 */
@Configuration
@EnableStateMachine
public class TrafficLightStateConfig extends EnumStateMachineConfigurerAdapter<TrafficLightStates, TrafficLightEvents> {

    @Autowired
    TimingFacade timingFacade;

    @Autowired
    StateMachineListenerAdapter<TrafficLightStates,TrafficLightEvents> listener;

    /**
     * Start the statemachine automatically and add a listener for the state transitions
     * @link com.edxamples.statemachine.TrafficLightListener
     * @param config the objects used for the States and events
     * @throws Exception if it fails to create the config
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<TrafficLightStates, TrafficLightEvents> config)
            throws Exception {
        config.withConfiguration().autoStartup(true).listener(listener);
    }

    /**
     * Sets up the states and which is the initial state
     * @param states the set of states to transitions through
     * @throws Exception if the configuration setup fails
     */
    @Override
    public void configure(StateMachineStateConfigurer<TrafficLightStates, TrafficLightEvents> states)
            throws Exception {
        states
                .withStates()
                .initial(OFF)
                .states(EnumSet.allOf(TrafficLightStates.class));
    }

    /**
     * Configuration for transitions between the states
     * The transition should work as such this transition plan is seen in the README.md
     *
     * @param transitions the object used for adding transitions between states
     * @throws Exception the exception thrown by adding state transitions from the transitions object
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<TrafficLightStates, TrafficLightEvents> transitions)
            throws Exception {
        // timerOnce dictates the time that should elapse before going into the next state and not just the one timer
        transitions
                .withExternal()
                .source(OFF).target(GREEN_MAJOR).event(TrafficLightEvents.TURN_ON)
                .and().withExternal()
                .source(GREEN_MAJOR).target(AMBER_MAJOR).timerOnce(timingFacade.getTimeFor(GREEN_MAJOR))
                .and().withExternal()
                .source(AMBER_MAJOR).target(RED_MAJOR).timerOnce(timingFacade.getTimeFor(AMBER_MAJOR))
                .and().withExternal()
                .source(RED_MAJOR).target(GREEN_MINOR).timerOnce(timingFacade.getTimeFor(RED_MAJOR))
                .and().withExternal()
                .source(GREEN_MINOR).target(AMBER_MINOR).timerOnce(timingFacade.getTimeFor(GREEN_MINOR))
                .and().withExternal()
                .source(AMBER_MINOR).target(RED_MINOR).timerOnce(timingFacade.getTimeFor(AMBER_MINOR))
                .and().withExternal()
                .source(RED_MINOR).target(GREEN_MAJOR).timerOnce(timingFacade.getTimeFor(RED_MINOR));
    }
}
