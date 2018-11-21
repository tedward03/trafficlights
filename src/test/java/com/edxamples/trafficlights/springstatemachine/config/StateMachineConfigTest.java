package com.edxamples.trafficlights.springstatemachine.config;


import com.edxamples.trafficlights.config.StateMachineConfig;
import com.edxamples.trafficlights.shared.Events;
import com.edxamples.trafficlights.shared.States;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StateMachineConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineConfigTest {

    @Autowired
    StateMachine<States,Events> testableStateMachine;

    @Test
    public void testInitState() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine).step().expectState(States.RED).and().build();
        plan.test();
    }

    @Test
    public void testCycleOfLights() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.RED)
                .and().step().sendEvent(Events.LIGHTCHANGE)
                .and().step().expectState(States.GREEN)
                .and().step().sendEvent(Events.LIGHTCHANGE)
                .and().step().expectState(States.AMBER)
                .and().step().sendEvent(Events.LIGHTCHANGE)
                .and().step().expectState(States.RED)
                .and().build();
        plan.test();
    }
}