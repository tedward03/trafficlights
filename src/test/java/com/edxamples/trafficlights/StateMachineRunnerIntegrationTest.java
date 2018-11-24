package com.edxamples.trafficlights;

import com.edxamples.trafficlights.config.TrafficLightStateConfig;
import com.edxamples.trafficlights.statemachine.TrafficLightEvents;
import com.edxamples.trafficlights.statemachine.TrafficLightListener;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.TimingFacade;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.junit.Assert;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TrafficLightStateConfig.class,StateMachineRunnerIntegrationTest.Context.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineRunnerIntegrationTest {

    @Autowired
    StateMachine machine;

    @Autowired
    TrafficLightStarter runner;

    @Autowired
    TimingFacade timingFacade;

    @Autowired
    TrafficLightListener listener;

    @Test
    public void testRunStartsTransition() throws InterruptedException {
        runner.run();
        Thread.sleep(5);
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.OFF),is(TrafficLightStates.GREEN_MAJOR));
    }

    @Test
    public void testRunContinuesToAllStates() throws InterruptedException {
        Assert.assertThat(machine.getInitialState().getId(),is(TrafficLightStates.OFF));
        runner.run();
        //eww
        Thread.sleep(100);
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.GREEN_MAJOR),is(TrafficLightStates.AMBER_MAJOR));
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.AMBER_MAJOR),is(TrafficLightStates.RED_MAJOR));
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.RED_MAJOR),is(TrafficLightStates.GREEN_MINOR));
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.GREEN_MINOR),is(TrafficLightStates.AMBER_MINOR));
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.AMBER_MINOR),is(TrafficLightStates.RED_MINOR));
        Assert.assertThat(listener.getTransitionsHistory().get(TrafficLightStates.RED_MINOR),is(TrafficLightStates.GREEN_MAJOR));
    }

    /**
     * Test Config
     */
    @Configuration
    static class Context {

        @Bean
        public StateMachineListenerAdapter<TrafficLightStates, TrafficLightEvents> trafficLightListener(){
            return new TrafficLightListener();
        }

        @Bean
        public TrafficLightStarter trafficLightStarter(){
            return new TrafficLightStarter();
        }

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
        TimingInputHandler timingInputHandler(){
            return Mockito.mock(TimingInputHandler.class);
        }

        @Bean
        TimingsRepository timingsRepository(){
            return Mockito.mock(TimingsRepository.class);
        }

        @Bean
        JdbcTemplate jdbcTemplate(){
            return Mockito.mock(JdbcTemplate.class);
        }

    }

}