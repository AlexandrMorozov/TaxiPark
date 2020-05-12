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
   /* @NotNull
    @Column(name = "serviceid")
    private int serviceID;*/

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceid")
    private Services mainServiceData;


    public Customer_Services_Data()
    {

    }

    public Customer_Services_Data(double completionTime, int guaranteeTime, /*int serviceID*/Services mainServiceData)
    {
        this.completionTime=completionTime;
        this.guaranteeTime=guaranteeTime;
        //this.serviceID=serviceID;
        this.mainServiceData=mainServiceData;
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

    /*public int getServiceID() {
        return serviceID;
    }*/

    public Services getMainServiceData() {
        return mainServiceData;
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

   /* public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }*/

    public void setMainServiceData(Services mainServiceData) {
        this.mainServiceData = mainServiceData;
    }
}
