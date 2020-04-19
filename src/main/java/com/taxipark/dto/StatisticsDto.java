package com.taxipark.dto;


public class StatisticsDto
{
    private String dateOfOrder;
    private String serviceName;
    private /*long*/double value;

    public StatisticsDto(String dateOfOrder, String serviceName, long value)
    {
        this.dateOfOrder = dateOfOrder;
        this.serviceName=serviceName;
        this.value=value;
    }

    public StatisticsDto(String dateOfOrder, String serviceName, double value)
    {
        this.dateOfOrder = dateOfOrder;
        this.serviceName=serviceName;
        this.value=value;
    }

    public StatisticsDto(String dateOfOrder, String serviceName)
    {
        this.dateOfOrder = dateOfOrder;
        this.serviceName=serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getServiceName() {
        return serviceName;
    }

    public /*long*/double getValue() {
        return value;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }
}
