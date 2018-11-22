package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.Application;
import com.edxamples.trafficlights.StateMachineRunner;
import com.edxamples.trafficlights.shared.Events;
import com.edxamples.trafficlights.shared.States;
import org.apache.log4j.Logger;
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
                .initial(States.REDALL)
                .states(EnumSet.allOf(States.class));
    }

    /**
     * Configuration for transitions between the states
     * @param transitions the object used for adding transitions between states
     * @throws Exception the exception thrown by adding state transitions from the transitions object
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {

        transitions
                .withExternal()
                .source(States.REDALL).target(States.GREEN_NS).event(Events.LIGHTCHANGE_NS).action(stateAction())
                .and().withExternal()
                .source(States.GREEN_NS).target(States.AMBER_NS).event(Events.LIGHTCHANGE_NS).action(stateAction())
                .and().withExternal()
                .source(States.AMBER_NS).target(States.REDALL).event(Events.LIGHTCHANGE_NS).action(stateAction())
                .and().withExternal()
                .source(States.REDALL).target(States.GREEN_EW).event(Events.LIGHTCHANGE_EW).action(stateAction())
                .and().withExternal()
                .source(States.GREEN_EW).target(States.AMBER_EW).event(Events.LIGHTCHANGE_EW).action(stateAction())
                .and().withExternal()
                .source(States.AMBER_EW).target(States.REDALL).event(Events.LIGHTCHANGE_EW).action(stateAction());
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
