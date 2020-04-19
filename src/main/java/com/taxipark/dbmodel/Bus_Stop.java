package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Bus_Stop
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name="stopID")
    private int stopID;
    @NotNull
    @Column(name="name")
    private String name;

    @OneToMany(targetEntity = Route_Points.class, mappedBy = "routeID", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Route_Points> stopPoints;

    public Bus_Stop()
    {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public Set<Route_Points> getStopPoints() {
        return stopPoints;
    }

    public String getName() {
        return name;
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopPoints(Set<Route_Points> stopPoints) {
        this.stopPoints = stopPoints;
    }
}
