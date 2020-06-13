package com.taxipark.services;

import com.taxipark.repos.ServicesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderCostCalculator
{
    public double calculateTaxiOrderCost(int serviceID,double calculatablePrice)
    {
        return Math.round(calculatablePrice*(2+Math.random()*13));
    }

}
