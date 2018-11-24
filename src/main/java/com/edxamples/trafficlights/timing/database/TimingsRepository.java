package com.edxamples.trafficlights.timing.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;

/**
 * Used to query the in memory database for the right data
 */
@Repository
public class TimingsRepository  {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * this method retrieves the timings for the specified time and day
     * @param time the time needed
     * @param dayCode the dayCode MON/TUE/etc
     * @return the timings object used to specify the duration of both the green major and green minor
     */
    public Timings findByTimeAndDay(Time time, String dayCode){
        return jdbcTemplate.queryForObject(
                "select * from timings where startTime < ? and endTIme > ? and dayOfWeek = ? ", new Object[] { time, time, dayCode },
                new BeanPropertyRowMapper<>(Timings.class));
    }

}
