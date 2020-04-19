package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity
public class Arrival_Time
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="arrivaltimeid")
    private int arrivalTimeID;
    @NotNull
    @Column(name="typeofday")
    private String typeOfDay;
    @NotNull
    @Column(name="time")
    /*private Time time;*/
    private String time;
    @NotNull
    @Column(name="busstopid")
    private int busStopID;
    @NotNull
    @Column(name="routeid")
    private int routeID;
    @NotNull
    @Column(name="direction")
    private String direction;

    /*public Arrival_Time(int arrivalTimeID, String typeOfDay, Time time, int busStopID, int routeID, String direction)
    {
        this.time=time;
    }*/

    public Arrival_Time()
    {

    }

    public String getDirection() {
        return direction;
    }

    public int getBusStopID() {
        return busStopID;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getArrivalTimeID() {
        return arrivalTimeID;
    }

    public String getTypeOfDay() {
        return typeOfDay;
    }

   /* public Time getTime() {
        return time;
    }*/

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setArrivalTimeID(int arrivalTimeID) {
        this.arrivalTimeID = arrivalTimeID;
    }

    public void setBusStopID(int busStopID) {
        this.busStopID = busStopID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    /*public void setTime(Time time) {
        this.time = time;
    }*/

    public void setTypeOfDay(String typeOfDay) {
        this.typeOfDay = typeOfDay;
    }

    /////////////////////////////////////////////////////////


    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
