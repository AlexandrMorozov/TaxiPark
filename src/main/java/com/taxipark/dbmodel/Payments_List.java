package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Payments_List
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="paymentid")
    private int paymentID;
    @NotNull
    @Column(name="paymentdate")
    private String paymentDate;
    @NotNull
    @Column(name="paymentyear")
    private int paymentYear;
    @NotNull
    @Column(name="paymentmonth")
    private String paymentMonth;
    @NotNull
    @Column(name="paymentsum")
    private double paymentSum;
    @Column(name="personnelid")
    private Integer personnelID;
    @Column(name="serviceid")
    private Integer serviceID;
    @Column(name="servicecategoryid")
    private Integer serviceCategoryID;
    @NotNull
    @Column(name="paymenttype")
    private String paymentType;

    public Payments_List()
    {

    }

    public Payments_List(String paymentDate,double paymentSum)
    {
        this.paymentDate=paymentDate;
        this.paymentSum=paymentSum;
    }

    public Payments_List(int paymentYear)
    {
        this.paymentYear=paymentYear;

    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public void setPaymentYear(int paymentYear) {
        this.paymentYear = paymentYear;
    }

    public void setServiceCategoryID(Integer serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public void setPersonnelID(Integer personnelID) {
        this.personnelID = personnelID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentSum(double paymentSum) {
        this.paymentSum = paymentSum;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public int getPaymentYear() {
        return paymentYear;
    }

    public Integer getPersonnelID() {
        return personnelID;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public Integer getServiceCategoryID() {
        return serviceCategoryID;
    }
}
