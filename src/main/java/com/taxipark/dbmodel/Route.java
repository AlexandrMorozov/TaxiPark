package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Route
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private int routeID;
    @NotNull
    @Column(name="routenumber")
    private int routeNumber;
    @NotNull
    private String type;

    public Route()
    {

    }

    public int getRouteID() {
        return routeID;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public String getType() {
        return type;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

}
