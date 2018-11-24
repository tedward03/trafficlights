package com.edxamples.trafficlights.springstatemachine.config;


import com.edxamples.trafficlights.config.TrafficLightStateConfig;
import com.edxamples.trafficlights.statemachine.TrafficLightEvents;
import com.edxamples.trafficlights.statemachine.TrafficLightListener;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.TimingFacade;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TrafficLightStateConfig.class, TrafficLightStateConfigIntegrationTest.MockedExternals.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrafficLightStateConfigIntegrationTest {

    @Autowired
    StateMachine<TrafficLightStates,TrafficLightEvents> testableStateMachine;

    @Test
    public void testInitState() throws Exception {
        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<TrafficLightStates,TrafficLightEvents>builder()
                .stateMachine(testableStateMachine).step().expectState(TrafficLightStates.OFF).and().build();
        plan.test();
    }

    /**
     * Testing that the event fired in the right states causes the cycle of states
     * @throws Exception
     */
    @Test
    public void testEventTriggersStartOfCycle() throws Exception {

        StateMachineTestPlan plan =  StateMachineTestPlanBuilder.<TrafficLightStates,TrafficLightEvents>builder()
                .stateMachine(testableStateMachine)
                .step().expectState(TrafficLightStates.OFF).and().step().sendEvent(TrafficLightEvents.TURN_ON)
                .and().step().expectState(TrafficLightStates.GREEN_MAJOR)
                .and().build();
        plan.test();

    }

    @Configuration
    static class MockedExternals{

        /**
         * Mock the bean but since the statemachine when it boots will take the timings
         * we need to make sure the mock.when is in place before its called for the timings
         * @return
         */
        @Bean
        TimingFacade timingsFacade(){
            TimingFacade timingFacade = Mockito.mock(TimingFacade.class);
            when(timingFacade.getTimeFor(any(TrafficLightStates.class))).thenReturn(10L);
            return timingFacade;
        }

        @Bean
        TimingsRepository timingsRepository(){
            return Mockito.mock(TimingsRepository.class);
        }

        @Bean
        JdbcTemplate jdbcTemplate(){
            return Mockito.mock(JdbcTemplate.class);
        }

        @Bean
        TimingInputHandler timingInputHandler(){
            return Mockito.mock(TimingInputHandler.class);
        }

        @Bean
        public StateMachineListenerAdapter<TrafficLightStates, TrafficLightEvents> trafficLightListener(){
            return new TrafficLightListener();
        }

    }
}