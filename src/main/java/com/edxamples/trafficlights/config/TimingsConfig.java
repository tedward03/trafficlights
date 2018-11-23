package com.edxamples.trafficlights.config;

import com.edxamples.trafficlights.shared.Timings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimingsConfig {

    @Bean
    Timings timings(){
        return Timings.buildTimingsAllSameForLights(15000,2000);
    }

}
