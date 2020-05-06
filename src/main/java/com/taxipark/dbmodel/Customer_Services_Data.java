package com.taxipark.dbmodel;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Customer_Services_Data
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name="customerservicesdataid")
    private int customerServicesDataID;
    @NotNull
    @Column(name="completiontime")
    private double completionTime;
    @NotNull
    @Column(name="guaranteetime")
    private int guaranteeTime;
    @NotNull
    @Column(name = "serviceid")
    private int serviceID;


    public Customer_Services_Data()
    {

    }

    public Customer_Services_Data(double completionTime, int guaranteeTime, int serviceID)
    {
        this.completionTime=completionTime;
        this.guaranteeTime=guaranteeTime;
        this.serviceID=serviceID;
    }


    public int getCustomerServicesDataID() {
        return customerServicesDataID;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public int getGuaranteeTime() {
        return guaranteeTime;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setCustomerServicesDataID(int customerServicesDataID) {
        this.customerServicesDataID = customerServicesDataID;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    public void setGuaranteeTime(int guaranteeTime) {
        this.guaranteeTime = guaranteeTime;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
}
