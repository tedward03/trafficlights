package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.timing.TimingsFacade;
import com.edxamples.trafficlights.timing.services.TimingInputHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This constantTimes config is in a separate configuration class so I can easily override the bean in my test, by just not including it
 */
@Configuration
public class TimingsConfig {

    @Value("${day.code:''}")
    private String dayCode;

    @Value("${time:''}")
    private String time;

    @Bean
    TimingsFacade timingsFacade(){
        return new TimingsFacade();
    }

    @Bean
    TimingInputHandler timingInputHandler(){
        return new TimingInputHandler(time,dayCode);
    }

}
