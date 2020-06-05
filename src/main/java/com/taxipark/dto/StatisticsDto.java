package com.taxipark.dto;

public class StatisticsDto
{
    private String date;
    private double value;

    public StatisticsDto(String date,double value)
    {
        this.date=date;
        this.value = value;
    }

    public StatisticsDto(String date,long paymentNumber)
    {
        this.date=date;
        this.value =paymentNumber;
    }

    public double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
