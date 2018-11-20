package com.edxamples.trafficlights;

import org.apache.log4j.Logger;

public class Main {

    private final static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args){
        System.out.println("hello world");
        log.info("hello world info");
        log.trace("hello world trace");
        log.debug("hello world debug");
        log.error("hello world error");
    }
}
