create table IF NOT EXISTS timings
(
    id integer auto_increment not null,
    dayOfWeek varchar(255) not null,
    displayName varchar(255) not null,
    endTime time not null,
    green_Major_Duration_Millis bigint not null,
    green_Minor_Duration_Millis bigint not null,
    startTime time not null,
    primary key (id)
);

