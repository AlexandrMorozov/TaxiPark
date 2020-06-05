package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceid")
    private Services mainServiceData;


    public Customer_Services_Data()
    {

    }

    public Customer_Services_Data(double completionTime, int guaranteeTime, Services mainServiceData)
    {
        this.completionTime=completionTime;
        this.guaranteeTime=guaranteeTime;
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

    public void setMainServiceData(Services mainServiceData) {
        this.mainServiceData = mainServiceData;
    }
}
