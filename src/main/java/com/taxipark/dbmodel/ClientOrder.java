package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ClientOrder
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="orderid")
    private int orderID;
    @Column(name="clientid")
    private Integer clientID;
    @NotNull
    @Column(name="serviceid")
    private int serviceID;
    @NotNull
    @Column(name="cost")
    private double cost;
    @NotNull
    @Column(name="ordertype")
    private String orderType;
    @NotNull
    @Column(name="dateoforder")
    private String dateOfOrder;
    @NotNull
    @Column(name="timeoforder")
    private String timeOfOrder;
    @NotNull
    @Column(name="status")
    private String status;
    @Column(name="addinfo")
    private String addInfo;

    public ClientOrder()
    {

    }

    public ClientOrder(int serviceID,String dateOfOrder, String timeOfOrder)
    {
        this.serviceID=serviceID;
        this.dateOfOrder=dateOfOrder;
        this.timeOfOrder=timeOfOrder;
    }

    public ClientOrder(String timeOfOrder)
    {
        this.timeOfOrder=timeOfOrder;
    }

    public ClientOrder(Integer clientID,int serviceID, double cost, String orderType, String dateOfOrder,
                       String timeOfOrder, String status, String addInfo)

    {
        this.clientID=clientID;
        this.serviceID=serviceID;
        this.cost=cost;
        this.orderType=orderType;
        this.dateOfOrder=dateOfOrder;
        this.timeOfOrder=timeOfOrder;
        this.status=status;
        this.addInfo=addInfo;
    }

    public Integer getClientID() {
        return clientID;
    }

    public double getCost() {
        return cost;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeOfOrder() {
        return timeOfOrder;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
