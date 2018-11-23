package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.shared.Timings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This timings config is in a separate configuration class so I can easily override the bean in my test, by just not including it
 */
@Configuration
public class TimingsConfig {

    @Bean
    Timings timings(){
        return Timings.buildTimingsAllSameForLights(15000,2000);
    }

}
