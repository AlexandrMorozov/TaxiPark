package com.taxipark.dto;

public class ArrivalMinuteDisplayDto
{
    private String minute;

    public ArrivalMinuteDisplayDto()
    {

    }
    public ArrivalMinuteDisplayDto(String minute)
    {
        this.minute=minute;
    }

    public String getMinute()
    {
        return minute;
    }

    public void setMinute(String minute)
    {
        this.minute = minute;
    }
}
