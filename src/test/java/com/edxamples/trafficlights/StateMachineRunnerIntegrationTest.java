package com.edxamples.trafficlights;

import com.edxamples.trafficlights.config.StateMachineConfig;
import com.edxamples.trafficlights.statemachine.States;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StateMachineConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineRunnerIntegrationTest {

    @Autowired
    StateMachine machine;

    @Autowired
    StateMachineRunner runner;

    @Test
    public void testRun() throws InterruptedException {
        runner.run(false);
    }

    @Test
    public void testStepThrough() throws InterruptedException {
        //init
        TimeUnit testUnit = TimeUnit.MILLISECONDS;
        Assert.assertThat(machine.getInitialState().getId(),is(States.GREEN_MAJOR_NO_EVENT_YET));
        //if
        runner.triggerTraffic(testUnit,2);
        //then
        Assert.assertThat(runner.getState(),is(States.AMBER_MAJOR));

    }

//    /** Test Config
//     */
//    @Configuration
//    static class ConstantTimesContext {
//
//        /**
//         * Add in a different constantTimes bean so that we can have a faster test
//         * @return the constantTimes bean only used for this test
//         */
//        @Bean
//        ConstantTimes timings(){
//            return ConstantTimes.buildTimingsAllSameForLights(15,2);
//        }
//
//    }

}