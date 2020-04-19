package com.taxipark.dbmodel;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Route_Points
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="routepointsid")
    private int routePointsID;
    @NotNull
    @Column(name="busstopid")
    private int busStopID;
    @NotNull
    @Column(name="routeid")
    private int routeID;
    @NotNull
    @Column(name="direction")
    private String direction;
    @NotNull
    @Column(name="stopnumber")
    private int stopNumber;

    public Route_Points()
    {

    }

    /*public int getStopIDA()
    {
        return busStopID.getStopID();
    }*/

    public int getRouteID()
    {
        return routeID;
    }

    public int getBusStopID() {
        return busStopID;
    }

    public int getRoutePointsID() {
        return routePointsID;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void setBusStopID(int busStopID) {
        this.busStopID = busStopID;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRoutePointsID(int routePointsID) {
        this.routePointsID = routePointsID;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

/////////////////////////////////////////////////////////
    /*public void setBusStopID(Bus_Stop busStopID) {
        this.busStopID = busStopID;
    }*/

    /*public Bus_Stop getBusStopID() {
        return busStopID;
    }*/
}
