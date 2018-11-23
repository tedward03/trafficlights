package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.Application;
import com.edxamples.trafficlights.StateMachineRunner;
import com.edxamples.trafficlights.shared.Events;
import com.edxamples.trafficlights.shared.States;
import com.edxamples.trafficlights.shared.Timings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    @Autowired
    Timings timings;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config.withConfiguration().autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.GREEN_MAJOR)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * Configuration for transitions between the states
     * The transition should work as such this transition plan is seen in the README.md
     *
     * @param transitions the object used for adding transitions between states
     * @throws Exception the exception thrown by adding state transitions from the transitions object
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.GREEN_MAJOR).target(States.AMBER_MAJOR).event(Events.MINOR_ROAD_TRAFFIC).action(stateAction())
                .and().withExternal()
                .source(States.AMBER_MAJOR).target(States.RED_MAJOR)
                .timerOnce(timings.getAmberMajorToRedMajorMillis()).action(stateAction())
                .and().withExternal()
                .source(States.RED_MAJOR).target(States.GREEN_MINOR)
                .timerOnce(timings.getRedMajorToGreenMinorMillis()).action(stateAction())
                .and().withExternal()
                .source(States.GREEN_MINOR).target(States.AMBER_MINOR)
                .timerOnce(timings.getGreenMinorToAmberMinorMillis()).action(stateAction())
                .and().withExternal()
                .source(States.AMBER_MINOR).target(States.RED_MINOR)
                .timerOnce(timings.getAmberMinorToRedMinorMillis()).action(stateAction())
                .and().withExternal()
                .source(States.RED_MINOR).target(States.GREEN_MAJOR)
                .timerOnce(timings.getRedMinorToGreenMajorMillis()).action(stateAction());
    }

    /**
     * using the state context we can dictate what actions happen on each state
     * @return the actions to take on each state
     */
    @Bean
    Action<States, Events> stateAction() {
        return stateContext -> log.info("State change to " + stateContext.getTarget().getId());
    }

    @Bean
    StateMachineRunner runner() {
        return new StateMachineRunner();
    }

}
