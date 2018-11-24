package com.edxamples.trafficlights.timing.database;

import com.edxamples.trafficlights.Application;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class TimingsRepositoryIntegrationTest {

    private final static Logger log = org.apache.log4j.Logger.getLogger(Application.class);

    @Autowired
    TimingsRepository repository;

    @Autowired
    DataSource dataSource;

    @Before
    public void init() {
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(new ClassPathResource("schema.sql")));
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new EncodedResource(new ClassPathResource("data.sql")));
        }catch(SQLException e){
            log.info("error setting up db",e);
        }
    }

    @Test
    public void testFindByDayAndTimeAndTestTimingsMethods(){
        Timings timings = new Timings(4,
                "MON",
                Time.valueOf("09:00:01"),
                Time.valueOf("16:00:00"),
                15000L,
                15000L,
                "Midday Monday");
        Timings dbTimings= repository.findByTimeAndDay(Time.valueOf("09:30:00"),"MON");
        Assert.assertEquals(timings.getId(),dbTimings.getId());
        Assert.assertEquals(timings.getGreenMajorDurationMillis(),dbTimings.getGreenMajorDurationMillis());
        Assert.assertEquals(timings.getGreenMinorDurationMillis(),dbTimings.getGreenMinorDurationMillis());
        Assert.assertEquals(timings.getDayOfWeek(),dbTimings.getDayOfWeek());
        Assert.assertEquals(timings.getDisplayName(),dbTimings.getDisplayName());
        Assert.assertEquals(timings.getEndTime(),dbTimings.getEndTime());
        Assert.assertEquals(timings.getStartTime(),dbTimings.getStartTime());
        Assert.assertNotNull(dbTimings.toString());
    }

    @Configuration
    public static class Context{

        @Bean
        TimingsRepository timingsRepository(){
            return new TimingsRepository();
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public static DataSource productDataSource() {
            DriverManagerDataSource dataSource
                    = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:h2:mem:testdb");
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

    }

}