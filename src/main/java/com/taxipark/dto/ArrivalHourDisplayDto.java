package com.taxipark.dto;

import java.util.List;

public class ArrivalHourDisplayDto
{
    private String hour;
    private Iterable<ArrivalMinuteDisplayDto> minutes;

    public ArrivalHourDisplayDto()
    {

    }

    public ArrivalHourDisplayDto(String hour, List<ArrivalMinuteDisplayDto> minutes)
    {
        this.hour=hour;
        this.minutes = minutes;
    }

    public Iterable<ArrivalMinuteDisplayDto> getMinutes() {
        return minutes;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinutes(Iterable<ArrivalMinuteDisplayDto> minutes) {
        this.minutes = minutes;
    }
}
