package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name = "cargoweight")
    private Double cargoWeight;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid")
    private ClientOrder clientOrder;

    public Order_Route()
    {

    }

    public Order_Route(String placeOfDeparture, String placeOfArrival,Double cargoWeight, ClientOrder mainOrder)
    {
        this.placeOfDeparture=placeOfDeparture;
        this.placeOfArrival=placeOfArrival;
        this.cargoWeight=cargoWeight;
        this.clientOrder=mainOrder;
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

    public ClientOrder getMainOrder() {
        return clientOrder;
    }

    public Double getCargoWeight() {
        return cargoWeight;
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

    public void setMainOrder(ClientOrder mainOrder) {
        this.clientOrder = mainOrder;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
}
