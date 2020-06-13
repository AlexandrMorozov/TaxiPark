package com.taxipark.services;

import com.taxipark.dbmodel.ClientOrder;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dbmodel.Positions;
import com.taxipark.repos.ClientOrderRepo;
import com.taxipark.repos.PersonnelRepo;
import com.taxipark.repos.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOrderAssigner
{
    @Autowired
    PositionsRepo positionsRepo;
    @Autowired
    PersonnelRepo personnelRepo;
    @Autowired
    ClientOrderRepo clientOrderRepo;

    public Personnel selectCustomerOrderReceiver()
    {
        Positions position=positionsRepo.findByPositionName("Мастер-приёмщик");

        List<Personnel> allPositionPersonnel=personnelRepo.findByPersonnelProfession(position);

        Personnel selectedEmployee=selectOrderReceiver(allPositionPersonnel);

        return selectedEmployee;
    }

    public Personnel selectCargoTaxiOrderReceiver()
    {
        Positions position=positionsRepo.findByPositionName("Водитель");

        List<Personnel> allPositionPersonnel=personnelRepo.findByProfessionAndTransportType(position,"Грузовой");

        Personnel selectedEmployee=selectOrderReceiver(allPositionPersonnel);

        return selectedEmployee;
    }

    private Personnel selectOrderReceiver(List<Personnel> allPositionPersonnel)
    {
        int currentReceiver=0;
        int receiversOrderCount=clientOrderRepo.findByAssignedEmployeeAndStatus(allPositionPersonnel.get(0),"active").size();

        for(int i=0;i<allPositionPersonnel.size();i++)
        {
            List<ClientOrder> activeOrders=clientOrderRepo.findByAssignedEmployeeAndStatus(allPositionPersonnel.get(i),"active");

            if(activeOrders.size()<receiversOrderCount)
            {
                currentReceiver=i;
                receiversOrderCount=activeOrders.size();
            }
        }

        return allPositionPersonnel.get(currentReceiver);
    }
}
