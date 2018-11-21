package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.Application;
import com.edxamples.trafficlights.States;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig
        extends StateMachineConfigurerAdapter<States, String> {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, String> config)
            throws Exception {
        config.withConfiguration().autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, String> states)
            throws Exception {
        states
                .withStates()
                .initial(States.RED)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.RED).target(States.GREEN).event("lightChange").action(stateAction())
                .and().withExternal()
                .source(States.GREEN).target(States.AMBER).event("lightChange").action(stateAction())
                .and().withExternal()
                .source(States.AMBER).target(States.RED).event("lightChange").action(stateAction());
    }

    @Bean
    public Action<States, String> stateAction() {
        return stateContext -> log.info("State change to " + stateContext.getTarget().getId());
    }
}
