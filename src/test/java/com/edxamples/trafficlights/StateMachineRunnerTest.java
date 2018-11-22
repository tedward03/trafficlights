package com.edxamples.trafficlights;

import com.edxamples.trafficlights.config.StateMachineConfig;
import com.edxamples.trafficlights.shared.States;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StateMachineConfig.class})
public class StateMachineRunnerTest {

    @Autowired
    StateMachine machine;

    @Autowired
    StateMachineRunner runner;

    @Test
    public void testStepThrough() throws InterruptedException {
        Assert.assertThat(machine.getInitialState().getId(),is(States.REDALL));
        runner.stepThrough();
        Assert.assertThat(runner.getState(),is(States.GREEN_EW));
        runner.stepThrough();
        runner.stepThrough();
        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(true));
        Assert.assertThat(runner.getState(),is(States.REDALL));
        runner.stepThrough();
        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(true));
        Assert.assertThat(runner.getState(),is(States.GREEN_NS));
        runner.stepThrough();
        runner.stepThrough();
        Assert.assertThat(runner.isNorthSouthTimeToGo(),is(false));
        Assert.assertThat(runner.getState(),is(States.REDALL));
    }


}