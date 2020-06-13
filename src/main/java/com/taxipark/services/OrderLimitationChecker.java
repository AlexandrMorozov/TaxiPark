package com.taxipark.services;

import com.taxipark.repos.ClientOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderLimitationChecker
{
    @Autowired
    ClientOrderRepo clientOrderRepo;

    public boolean checkPreOrderLimit(String date,int clientID,String type)
    {
        String[] weekMarginDays= calculateWeekMarginDays(date);

        int numberOfOrderedServices=clientOrderRepo.
                findNumOfClientOrdersPerWeek(clientID, type,
                        weekMarginDays[0],weekMarginDays[1]);

        if(numberOfOrderedServices>3)
        {
            return true;
        }

        return false;
    }

    //Similar to 1st method but with the phone number
    public boolean checkPreOrderLimit(String date,String phone,String type)
    {
        String[] weekMarginDays= calculateWeekMarginDays(date);

        int numberOfOrderedServices=clientOrderRepo.
                findNumOfClientOrdersPerWeek(phone, type,
                        weekMarginDays[0],weekMarginDays[1]);

        if(numberOfOrderedServices>3)
        {
            return true;
        }

        return false;
    }

    public String[] calculateWeekMarginDays(String date)
    {
        String[] parsedDate=date.split("-");

        LocalDate currentDay=LocalDate.of(Integer.parseInt(parsedDate[0]),
                                          Integer.parseInt(parsedDate[1]),
                                          Integer.parseInt(parsedDate[2]));

        int currentWeekDayNumber=currentDay.getDayOfWeek().getValue();

        LocalDate firstDayOfTheWeek=currentDay.
                minusDays(currentWeekDayNumber-1);
        LocalDate lastDayOfTheWeek=currentDay.
                plusDays(7-currentWeekDayNumber);

        return new String[]{firstDayOfTheWeek.toString(),
                lastDayOfTheWeek.toString()};
    }


    public boolean checkInstantOrderLimit(int clientID, String type)
    {
        int numberOfOrderedServices=clientOrderRepo.
                findNumOfActiveClientOrders(clientID,type,"active");

        if(numberOfOrderedServices>0)
        {
            return true;
        }

        return false;
    }

    //Similar to 1st method but with the phone number
    public boolean checkInstantOrderLimit(String phone, String type)
    {
        int numberOfOrderedServices=clientOrderRepo.
                findNumOfActiveClientOrders(phone,type,"active");

        if(numberOfOrderedServices>0)
        {
            return true;
        }

        return false;
    }
}
