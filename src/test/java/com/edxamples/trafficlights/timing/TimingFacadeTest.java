package com.edxamples.trafficlights.timing;

import com.edxamples.trafficlights.config.TimingsConfig;
import com.edxamples.trafficlights.statemachine.TrafficLightStates;
import com.edxamples.trafficlights.timing.database.Timings;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TimingsConfig.class,TimingFacadeTest.Context.class})
public class TimingFacadeTest {

    @Autowired
    TimingFacade facade;

    @Test
    public void testGetValue() throws Exception {
     facade.setup();
     Long timeExample = facade.getTimeFor(TrafficLightStates.GREEN_MAJOR);
     Assert.assertEquals(15000L,timeExample.longValue());
    }

    @Configuration
    public static class Context {

        @Bean
        TimingsRepository timingsRepository(){
            TimingsRepository timingsRepository = Mockito.mock(TimingsRepository.class);
            Timings timings = new Timings(4,
                    "MON",
                    Time.valueOf("09:00:01"),
                    Time.valueOf("16:00:00"),
                    15000L,
                    15000L,
                    "Midday Monday");
            when(timingsRepository.findByTimeAndDay(any(),any())).thenReturn(timings);
            return timingsRepository;
        }

        @Bean
        JdbcTemplate jdbcTemplate(){
            return Mockito.mock(JdbcTemplate.class);
        }

    }
}