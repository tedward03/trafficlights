package com.edxamples.trafficlights.springstatemachine.config;


import com.edxamples.trafficlights.config.StateMachineConfig;
import com.edxamples.trafficlights.config.TimingsConfig;
import com.edxamples.trafficlights.statemachine.Events;
import com.edxamples.trafficlights.statemachine.States;
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
@SpringBootTest(classes = { StateMachineConfig.class, TimingsConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineConfigTest {

    @Autowired
    StateMachine<States,Events> testableStateMachine;

    @Test
    public void testInitState() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine).step().expectState(States.GREEN_MAJOR_NO_EVENT_YET).and().build();
        plan.test();
    }

    /**
     * Testing that the event fired in the right states causes the cycle of states
     * @throws Exception
     */
    @Test
    public void testEventTriggersStartOfCycle() throws Exception {

        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.GREEN_MAJOR_NO_EVENT_YET)
                .and().step().sendEvent(Events.MINOR_ROAD_TRAFFIC)
                .and().build();
        plan.test();

    }
}