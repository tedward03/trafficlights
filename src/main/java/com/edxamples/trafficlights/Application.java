package com.edxamples.trafficlights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * MAin application class
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    StateMachineRunner runner;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
        runner.run(true);
    }

}
