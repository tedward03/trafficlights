package com.edxamples.trafficlights;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    @Autowired
    private StateMachine<States, String> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
       log.info("Init State: " + stateMachine.getInitialState().getId());
       while(true){
           TimeUnit.SECONDS.sleep(2);
           stateMachine.sendEvent("lightChange");
       }

    }

}
