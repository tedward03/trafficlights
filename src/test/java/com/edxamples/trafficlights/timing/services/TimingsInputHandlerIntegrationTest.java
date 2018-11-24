package com.edxamples.trafficlights.timing.services;

import com.edxamples.trafficlights.config.TimingsConfig;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.TimingFacade;
import com.edxamples.trafficlights.timing.database.TimingsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TimingsConfig.class,TimingsInputHandlerIntegrationTest.MockedExternals.class})
@TestPropertySource(properties = {
        "day.code=MON","time=07:30:00"
})
public class TimingsInputHandlerIntegrationTest {

    @Autowired
    TimingInputHandler timingInputHandler;

    @Test
    public void testInputFromConfigSource(){
        Assert.assertThat(timingInputHandler.getDayCode(),is("MON"));
        Assert.assertThat(timingInputHandler.getTime(),is(Time.valueOf("07:30:00")));
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
        public TimingsRepository timingsRepository() {
            return Mockito.mock(TimingsRepository.class);
        }

        @Bean
        JdbcTemplate jdbcTemplate(){
            return Mockito.mock(JdbcTemplate.class);
        }


    }


}