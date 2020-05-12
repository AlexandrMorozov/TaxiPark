package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    //@NotNull
    /*@Column(name="serviceid")
    private Integer serviceID;*/
   /* @Column(name = "presonnelid")
    private Integer personnelID;*/
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "clientOrder")
    private List<Order_Route> orderRoute;

    @ManyToOne(targetEntity = Services.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceid")
    private Services orderedService;

    @ManyToOne(targetEntity = Personnel.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "presonnelid")
    private Personnel assignedEmployee;


    public ClientOrder()
    {

    }

    public ClientOrder(/*int serviceID*/Services orderedService,String dateOfOrder, String timeOfOrder)
    {
        /*this.serviceID=serviceID;*/
        this.orderedService=orderedService;
        this.dateOfOrder=dateOfOrder;
        this.timeOfOrder=timeOfOrder;
    }

    public ClientOrder(String timeOfOrder)
    {
        this.timeOfOrder=timeOfOrder;
    }

    public ClientOrder(Integer clientID,/*int serviceID*/Services orderedService,/*Integer personnelID*/Personnel assignedEmployee, double cost, String orderType, String dateOfOrder,
                       String timeOfOrder, String status, String addInfo)

    {
        this.clientID=clientID;
       /* this.serviceID=serviceID;*/
        this.orderedService=orderedService;
        /*this.personnelID=personnelID;*/
        this.assignedEmployee=assignedEmployee;
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

  /*  public Integer getServiceID() {
        return serviceID;
    }*/

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

   /* public Integer getPersonnelID() {
        return personnelID;
    }*/

    public Personnel getAssignedEmployee() {
        return assignedEmployee;
    }

    /////////////////////

    public List<Order_Route> getOrderRoute() {
        return orderRoute;
    }

    public Services getOrderedService() {
        return orderedService;
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

   /* public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }*/

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeOfOrder(String timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /*public void setPersonnelID(Integer personnelID) {
        this.personnelID = personnelID;
    }*/

    public void setAssignedEmployee(Personnel assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    ///////////////////


    public void setOrderRoute(List<Order_Route> orderRoute) {
        this.orderRoute = orderRoute;
    }

    public void setOrderedService(Services orderedService) {
        this.orderedService = orderedService;
    }
}
