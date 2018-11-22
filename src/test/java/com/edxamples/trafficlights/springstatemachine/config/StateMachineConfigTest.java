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

    /**
     * test init state is REDALL
     * @throws Exception
     */
    @Test
    public void testInitState() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine).step().expectState(States.REDALL).and().build();
        plan.test();
    }

    /**
     * Testing that the event fired in the right states causes the cycle of states
     * @throws Exception
     */
    @Test
    public void testCycleOfLights() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.REDALL)
                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.GREEN_NS)
                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.AMBER_NS)
                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.REDALL)
                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.GREEN_EW)
                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.AMBER_EW)
                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.REDALL)
                .and().build();
        plan.test();
    }

    /**
     * testing that sending an event in the wrong state causes
     * the state to stay the same for green NS
     * @throws Exception
     */
    @Test
    public void testWrongEventInWrongStateGreenNS() throws Exception {
        // send wrong event when Green Ns and expect the state not to change
        StateMachineTestPlan planForTestGreen = StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.REDALL)

                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.GREEN_NS)

                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.GREEN_NS)

                .and().build();
        planForTestGreen.test();
    }

    /**
     * testing that sending an event in the wrong state causes
     * the state to stay the same for amber NS,
     * @throws Exception
     */
    @Test
    public void testWrongEventInWrongStateAmberNS() throws Exception {
        // send wrong event when Amber Ns and expect the state not to change
        StateMachineTestPlan planForTestAmber = StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.REDALL)

                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.GREEN_NS)

                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.AMBER_NS)

                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.AMBER_NS)

                .and().build();
        planForTestAmber.test();
    }

    /**
     * testing that sending an event in the wrong state causes
     * the state to stay the same for green EW,
     * @throws Exception
     */
    @Test
    public void testWrongEventInWrongStateGreenEW() throws Exception {
        // send wrong event when Green EW and expect the state not to change
        StateMachineTestPlan planForTestGreenEW = StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.REDALL)

                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.GREEN_EW)

                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.GREEN_EW)

                .and().build();
        planForTestGreenEW.test();
    }

    /**
     * testing that sending an event in the wrong state causes
     * the state to stay the same for green EW,
     * @throws Exception
     */
    @Test
    public void testWrongEventInWrongStateAmberEW() throws Exception {
        // send wrong event when Amber EW and expect the state not to change
        StateMachineTestPlan planForTestAmberEW =  StateMachineTestPlanBuilder.<States,Events>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(States.REDALL)

                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.GREEN_EW)

                .and().step().sendEvent(Events.LIGHTCHANGE_EW)
                .and().step().expectState(States.AMBER_EW)

                .and().step().sendEvent(Events.LIGHTCHANGE_NS)
                .and().step().expectState(States.AMBER_EW)

                .and().build();
        planForTestAmberEW.test();
    }
}