package com.taxipark;

import com.taxipark.config.OrderMessageHandler;
import com.taxipark.dbmodel.*;
import com.taxipark.repos.*;
import com.taxipark.logic.PossibleTimeCalcuator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
public class ServicesOrderingController
{

    @Autowired
    private ClientsRepo clientsRepo;
    @Autowired
    private ServicesRepo servicesRepo;
    @Autowired
    private Customer_Services_DataRepo customer_services_dataRepo;
    @Autowired
    private ClientOrderRepo clientOrderRepo;
    @Autowired
    private Order_RouteRepo order_routeRepo;
    @Autowired
    private PersonnelRepo personnelRepo;


    /////////////
    //@Autowired
    //private OrderMessageHandler test;
    ////////////

    private PossibleTimeCalcuator possibleTimeCalcuator=new PossibleTimeCalcuator();

    @PostMapping("/CreateTransportOrder")
    public String addTaxiOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "fromPoint") String fromPoint, @RequestParam(name = "toPoint") String toPoint,
                               @RequestParam(name = "name", required = false) String userName, @RequestParam(name = "telephone", required = false) String userTelephone,
                               @RequestParam(name = "comment") String comment, HttpSession session)
    {

        String login=(String) session.getAttribute("login");

        Services orderedService=servicesRepo.findByServicesID(serviceID);

        Integer clientID=null;

        double cost=Math.round(orderedService.getCalculatablePrice()*(2+Math.random()*13));
        /*orderedService.getCalculatablePrice()*(2+Math.random()*13)*/

        LocalDate currentDate=LocalDate.now();
        LocalTime currentTime=LocalTime.now();


        if(login==null)
        {
            comment=userName+"; "+userTelephone+"\n"+comment;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }

        ClientOrder newClientOrder=
                new ClientOrder(clientID,/*serviceID*/servicesRepo.findByServicesID(serviceID),/*45*/null,cost,"transport service",currentDate.toString(),currentTime.toString(),"active",comment);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint,null,newClientOrder/*.getOrderID()*/);
        order_routeRepo.save(newOrderRoute);

        return "redirect:/";
    }

    @PostMapping("/CreateCargoTransportOrder")
    public String addCargoTaxiOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "fromPoint") String fromPoint, @RequestParam(name = "toPoint") String toPoint,
                                    @RequestParam(name = "date") String date, @RequestParam(name = "time") String time, @RequestParam(name = "name", required = false) String userName,
                                    @RequestParam(name = "telephone", required = false) String userTelephone, @RequestParam(name = "comment") String comment,
                                    @RequestParam(name = "weight") int weight,HttpSession session)
    {
        String login=(String) session.getAttribute("login");

        Services orderedService=servicesRepo.findByServicesID(serviceID);
        Integer clientID=null;
        double cost=Math.round(0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice());
        /*0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice()*/;


        String weightData=weight+" кг;";
        comment=weightData+comment;

        if(login==null)
        {
            comment=userName+"; "+userTelephone+"\n"+comment;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }

        ClientOrder newClientOrder=
                new ClientOrder(clientID,/*serviceID*/servicesRepo.findByServicesID(serviceID),/*45*/null,cost,"transport service",date,time+":00","active",comment);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint,(double)weight,newClientOrder/*.getOrderID()*/);
        order_routeRepo.save(newOrderRoute);

        return "redirect:/";
    }

    @PostMapping("/CalculateOrderTime")
    public String getTimeSelectionMenu(@RequestParam(name = "id") int serviceID, @RequestParam(name = "date") String date,
                                       @RequestParam(name = "name", required = false) String userName,
                                       @RequestParam(name = "telephone", required = false) String userTelephone,
                                       @RequestParam(name = "comment") String comment, HttpSession session, Map<String,Object> model)
    {
        String response;

        String login=(String) session.getAttribute("login");
        List<ClientOrder> listOfSelectedDateClientOrders = clientOrderRepo.findTimesOfAllActiveOrdersByDay(date,"customer service","active");
        /*CompletionTimeDto*/Services orderedServiceDuration=servicesRepo./*findCompletionTime*/findByServicesID(serviceID);
        Iterable<ClientOrder> listOfPossibleOrderTime=
                possibleTimeCalcuator.countTimeGaps(listOfSelectedDateClientOrders, orderedServiceDuration.getServiceData().get(0).getCompletionTime()
                        /*getCompletionTime()*/,servicesRepo);

        if(listOfPossibleOrderTime==null)
        {
            response="redirect:/";
        }
        else
        {
            Services currentService=servicesRepo.findByServicesID(serviceID);
            Customer_Services_Data additionalServiceData=
                    customer_services_dataRepo.findByMainServiceData(currentService)/*findByServiceID(currentService.getServicesID())*/;

            response="user/DateTimeChoosingPage";
            model.put("service", currentService);
            model.put("addServiceData", additionalServiceData);
            model.put("orderTimes", listOfPossibleOrderTime);
            model.put("currentOrderDate",date);
            model.put("comment", comment);

            if(login==null)
            {
                model.put("nonAuthorized",true);
                model.put("clientName",userName);
                model.put("telephone",userTelephone);
            }
            else
            {
                model.put("nonAuthorized",false);
            }
        }

        return response;
    }

    @PostMapping("/CreateOrder")
    public String addCustomerServiceOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "ordDate") String date,
                                          @RequestParam(name = "orderTime") String time, @RequestParam(name = "name", required = false) String userName,
                                          @RequestParam(name = "telephone", required = false) String userTelephone, @RequestParam(name = "comment") String comment,
                                          HttpSession session)
    {

        String login=(String) session.getAttribute("login");

        Services orderedService=servicesRepo.findByServicesID(serviceID);

        Integer clientID=null;
        double cost=orderedService.getPrice();
        time=time+":00";

        if(login==null)
        {
            comment=userName+"; "+userTelephone+"\n"+comment;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }

        /////
        ClientOrder newClientOrder=new ClientOrder(clientID,/*serviceID*/servicesRepo.findByServicesID(serviceID),/*45*/null,cost,"customer service",date,time,"active",comment);

        clientOrderRepo.save(newClientOrder);


        return "redirect:/";
    }

    /*private void findAvailableCargoCar()
    {
        personnelRepo.
    }*/



}
