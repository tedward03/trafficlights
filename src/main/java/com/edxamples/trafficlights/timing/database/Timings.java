package com.edxamples.trafficlights.timing.database;

import javax.persistence.Id;
import javax.persistence.*;
import java.sql.Time;

@Table(name = "timings")
@Entity
public class Timings {

    @Id
    private int id;

    @Column(name = "DAY_OF_WEEK")
    private String dayOfWeek;

    @Column(name="START_TIME")
    private Time startTime;

    @Column(name="END_TIME")
    private Time endTime;

    @Column(name="GREEN_MAJOR_DURATION_MILLIS")
    private long greenMajorDurationMillis;

    @Column(name="GREEN_MINOR_DURATION_MILLIS")
    private long greenMinorDurationMillis;

    @Column(name="DISPLAY_NAME")
    private String displayName;

    public Timings() {
    }

    public Timings(int id, String dayOfWeek, Time startTime, Time endTime, long greenMajorDurationMillis, long greenMinorDurationMillis, String displayName) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.greenMajorDurationMillis = greenMajorDurationMillis;
        this.greenMinorDurationMillis = greenMinorDurationMillis;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public long getGreenMajorDurationMillis() {
        return greenMajorDurationMillis;
    }

    public void setGreenMajorDurationMillis(long greenMajorDurationMillis) {
        this.greenMajorDurationMillis = greenMajorDurationMillis;
    }

    public long getGreenMinorDurationMillis() {
        return greenMinorDurationMillis;
    }

    public void setGreenMinorDurationMillis(long greenMinorDurationMillis) {
        this.greenMinorDurationMillis = greenMinorDurationMillis;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() + ", " +
                "Day of Week: " + this.getDayOfWeek() + ", " +
                "State time: " + this.getStartTime() + ", " +
                "End Time: " + this.getEndTime() + ", " +
                "Green Major Duration: " + this.getGreenMajorDurationMillis() + ", " +
                "Green Minor Duration: " + this.getGreenMinorDurationMillis() + ", " +
                "Display Name: " + this.getDisplayName();
    }
}

