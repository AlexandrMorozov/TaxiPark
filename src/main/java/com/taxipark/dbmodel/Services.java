package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Services
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="servicesid")
    private int servicesID;
    @NotNull
    @Column(name="servicename")
    private String serviceName;
    @NotNull
    @Column(name="servicedescription")
    private String serviceDescription;
    @NotNull
    @Column(name="foto")
    private String foto;
    @NotNull
    @Column(name="price")
    private double price;
    @Column(name="calculatableprice")
    private Double calculatablePrice;
    @NotNull
    @Column(name="categoryid")
    private int categoryID;
    @Column(name="customerservicesdataid")
    private Integer customerServicesDataID;

    @OneToMany(targetEntity = ClientOrder.class, mappedBy = "serviceID", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Route_Points> orders;


    public Services()
    {

    }

    public Services(String serviceName,String serviceDescription,String foto,double price,
                    Double calculatablePrice,int categoryID,Integer customerServicesDataID)
    {
        this.serviceName=serviceName;
        this.serviceDescription=serviceDescription;
        this.foto=foto;
        this.price=price;
        this.calculatablePrice=calculatablePrice;
        this.categoryID=categoryID;
        this.customerServicesDataID=customerServicesDataID;
    }

    public Services(int servicesID,String serviceName,double price, Double calculatablePrice)
    {
        this.servicesID=servicesID;
        this.serviceName=serviceName;
        this.price=price;
        this.calculatablePrice=calculatablePrice;
    }

    public /*double*/Double getCalculatablePrice() {
        return calculatablePrice;
    }

    public int getServicesID() {
        return servicesID;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public /*int*/Integer getCustomerServicesDataID() {
        return customerServicesDataID;
    }

    public String getFoto() {
        return foto;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServicesID(int servicesID) {
        this.servicesID = servicesID;
    }

    public void setCalculatablePrice(/*double*/Double calculatablePrice) {
        this.calculatablePrice = calculatablePrice;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public void setCustomerServicesDataID(/*int*/Integer customerServicesDataID) {
        this.customerServicesDataID = customerServicesDataID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}