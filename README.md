# Traffic Lights
**Traffic lights state machine v2.0**

Build with <code>mvn clean install</code>

Run using <code>java -jar traffic-light-{version}.jar --day.code={daycode} --time={time} </code>

There are additional parameters now that can change the time and day for the stateMachine 

Optional params

<code> --day.code="MON" --time="09:00:00" </code> 

they both must be of the format "DDD" and "HH:MM:SS" respectively

If they are in the wrong format or you choice is to  not give them, on start up you will get some warnings.
But it will be fine those parameters will just default to the time now.

<code> WARN The time specified in args didn't match the format we needed (HH:mm:ss) so defaulting to right now : 21:27:23</code>

I used a h2 database to store the timings , but only the ones for the green major and minor , due to the other states usually having a constant time anyways


**Traffic lights state machine v1.1**

Run using <code>java -jar traffic-light-{version}.jar</code>

(N.B give the traffic event time to fire) 20s.
You should see a log for it firing "Traffic arrives at minor road"

Transitions
The traffic light should show the transition that a set of traffic light 4 of them (Crossroads)
2 for the major road and two for the minor road , their states are dictated through the States.class

it should cyclical ie they should loop back on themselves

it should go 
* Red for all
* Green for major road 
* Amber for the major road
* Red for all
* Green for minor road
* Amber for minor road
* Red for all
* Loop back

These are reflected in the States with the exception of red for all
being separated into two states to indicate the next Green 
* RED_BEFORE_MAJOR
* RED_BEFORE_MINOR

The loop is triggered by an event happening "Traffic on minor road"
When this happens the the loop state change will occur
leaving 2 seconds between each light change and then heading back to Green for the major road.


**Previous Solution (v1.0) Summary**

Originally that the traffic lights worked a little differently
You can see that from the git version of StateMachineRunner.class that there were many events being fired,
I felt that for actual traffic lights this is not the case.
Usually in traffic lights the major road will stay green until there is traffic on the other parallel lanes. 
And is was this behaviour that I tried to replicate
So there is only one event that fires and that is the traffic on minor road (MINOR_ROAD_TRAFFIC)
This Single event should trigger the cyclical loop and then it should loop back to green on the major road.

   
