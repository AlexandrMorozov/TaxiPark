package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Order_Route
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name="routeid")
    private int routeID;
    @NotNull
    @Column(name="placeofdeparture")
    private String placeOfDeparture;
    @NotNull
    @Column(name="placeofarrival")
    private String placeOfArrival;
    @NotNull
    @Column(name = "orderid")
    private int orderID;

    public Order_Route()
    {

    }

    public Order_Route(String placeOfDeparture, String placeOfArrival, int orderID)
    {
        this.placeOfDeparture=placeOfDeparture;
        this.placeOfArrival=placeOfArrival;
        this.orderID=orderID;
    }

    public int getRouteID() {
        return routeID;
    }

    public String getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public String getPlaceOfArrival() {
        return placeOfArrival;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void setPlaceOfDeparture(String placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public void setPlaceOfArrival(String placeOfArrival) {
        this.placeOfArrival = placeOfArrival;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
