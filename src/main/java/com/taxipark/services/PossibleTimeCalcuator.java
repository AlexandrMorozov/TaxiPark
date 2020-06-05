package com.taxipark.services;

import com.taxipark.dbmodel.Services;
import com.taxipark.repos.ServicesRepo;
import com.taxipark.dbmodel.ClientOrder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PossibleTimeCalcuator
{
    public List<ClientOrder> countTimeGaps(List<ClientOrder> listOfOrders, double userOrderCompletionTime, ServicesRepo servicesRepo)
    {
        List<ClientOrder> listOfPossibleOrders;

        LocalTime startOfWork=LocalTime.of(9,0);
        LocalTime endOfWork=LocalTime.of(20,0);

        ArrayList<LocalTime> listOfCurrentDateTimestamps=new ArrayList<>();

        if(listOfOrders.size()==0)
        {
            listOfPossibleOrders=new ArrayList<>();
            listOfPossibleOrders.addAll(calculatePossibleOrderTime(startOfWork,endOfWork,0.5));
        }
        else
        {
            listOfPossibleOrders=new ArrayList<>();

            for(int i=0;i<listOfOrders.size();i++)
            {
                String[] time=listOfOrders.get(i).getTimeOfOrder().split(":");
                listOfCurrentDateTimestamps.add(LocalTime.of(Integer.parseInt(time[0]),Integer.parseInt(time[1])));
            }


            for(int i=0;i<listOfCurrentDateTimestamps.size();i++)
            {
                Services completionTime=servicesRepo./*findCompletionTime*/findByServicesID(listOfOrders.get(i).getOrderedService().getServicesID());

                //CompletionTimeDto completionTimeDto=servicesRepo.findCompletionTime(listOfOrders.get(i).getOrderedService().getServicesID()/*getServiceID()*/);

                int orderDuration=(int)(/*completionTimeDto.getCompletionTime()*/completionTime.getServiceData().get(0).getCompletionTime()*60);
                LocalTime currentOrderTime=listOfCurrentDateTimestamps.get(i).plusMinutes(orderDuration);

                if(i!=(listOfCurrentDateTimestamps.size()-1))
                {
                    if(i==0)
                    {
                        listOfPossibleOrders.addAll(calculatePossibleOrderTime(startOfWork,listOfCurrentDateTimestamps.get(i),userOrderCompletionTime));
                    }

                    listOfPossibleOrders.addAll(calculatePossibleOrderTime(currentOrderTime,listOfCurrentDateTimestamps.get(i+1),userOrderCompletionTime));
                }
                else
                {
                    listOfPossibleOrders.addAll(calculatePossibleOrderTime(currentOrderTime,endOfWork,userOrderCompletionTime));
                }

            }
        }

        return listOfPossibleOrders;

    }

    private List<ClientOrder> calculatePossibleOrderTime(LocalTime currentOrder, LocalTime nextOrder, double userOrderCompletionTime)
    {
        List<ClientOrder> listOfPossibleOrders=new ArrayList<>();

        int nextTimeMinutesTotal=nextOrder.getHour()*60+nextOrder.getMinute();
        int currentTimeMinutesTotal=currentOrder.getHour()*60+currentOrder.getMinute();
        int freeTimeDelta=nextTimeMinutesTotal-currentTimeMinutesTotal;
        int num=freeTimeDelta/(int)(userOrderCompletionTime*60);

        LocalTime st=LocalTime.of(currentOrder.getHour(),currentOrder.getMinute());

        for(int j=0;j<num;j++)
        {
            if(j==0)
            {
                listOfPossibleOrders.add(new ClientOrder(st.toString()));
            }
            else
            {
                st=st.plusMinutes((int)(userOrderCompletionTime*60));
                listOfPossibleOrders.add(new ClientOrder(st.toString()));
            }
        }

        return listOfPossibleOrders;
    }
}
