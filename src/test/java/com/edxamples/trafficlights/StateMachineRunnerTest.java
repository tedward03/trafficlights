package com.edxamples.trafficlights;

import com.edxamples.trafficlights.config.StateMachineConfig;
import com.edxamples.trafficlights.shared.States;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StateMachineConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineRunnerTest {

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
        Assert.assertThat(machine.getInitialState().getId(),is(States.REDALL));
        //if
        runner.stepThrough(testUnit,3);
        //then
        Assert.assertThat(runner.getState(),is(States.GREEN_EW));

        runner.stepThrough(testUnit,2);
        runner.stepThrough(testUnit,2);

        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(true));
        Assert.assertThat(runner.getState(),is(States.REDALL));

        runner.stepThrough(testUnit,2);

        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(true));
        Assert.assertThat(runner.getState(),is(States.GREEN_NS));

        runner.stepThrough(testUnit,2);
        runner.stepThrough(testUnit,2);

        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(false));
        Assert.assertThat(runner.getState(),is(States.REDALL));
    }


}