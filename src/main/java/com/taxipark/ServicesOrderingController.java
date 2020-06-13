package com.taxipark;

import com.taxipark.dbmodel.*;
import com.taxipark.repos.*;
import com.taxipark.services.ClientOrderAssigner;
import com.taxipark.services.PossibleTimeCalcuator;
import com.taxipark.services.OrderLimitationChecker;
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
    private OrderLimitationChecker orderLimitationChecker;
    @Autowired
    private PossibleTimeCalcuator possibleTimeCalcuator;
    @Autowired
    private ClientOrderAssigner clientOrderAssigner;

    @PostMapping("/CreateTransportOrder")
    public String addTaxiOrder(@RequestParam(name = "id") int serviceID,
                               @RequestParam(name = "fromPoint") String fromPoint,
                               @RequestParam(name = "toPoint") String toPoint,
                               @RequestParam(name = "telephone", required = false) String userTelephone,
                               HttpSession session)
    {

        String login=(String) session.getAttribute("login");

        Integer clientID=null;
        String unauthorizedClientContactInfo=null;
        boolean isOrderLimitReached;

        if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
            isOrderLimitReached=orderLimitationChecker.checkInstantOrderLimit(userTelephone,"transport service");
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
            isOrderLimitReached=orderLimitationChecker.checkInstantOrderLimit(clientID,"transport service");
        }

        //OrderMessageHandler.getInstanse().test();

        if(isOrderLimitReached)
        {
            return "";
        }

        Services orderedService=servicesRepo.findByServicesID(serviceID);


        double cost=Math.round(orderedService.getCalculatablePrice()*(2+Math.random()*13));
        /*orderedService.getCalculatablePrice()*(2+Math.random()*13)*/

        LocalDate currentDate=LocalDate.now();
        LocalTime currentTime=LocalTime.now();

       /* System.out.println("redy");
        WebSocketHandler.getInstance().assignTaxiOrder(login);*/


       /* if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }*/

        ClientOrder newClientOrder=
                new ClientOrder(clientID,orderedService,null,cost,"transport service",
                        currentDate.toString(),currentTime.toString(),"active",/*comment*/null,unauthorizedClientContactInfo);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint,null,newClientOrder);
        order_routeRepo.save(newOrderRoute);

        return "redirect:/";
    }

    @PostMapping("/CreateCargoTransportOrder")
    public String addCargoTaxiOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "fromPoint") String fromPoint,
                                    @RequestParam(name = "toPoint") String toPoint, @RequestParam(name = "date") String date,
                                    @RequestParam(name = "time") String time, @RequestParam(name = "telephone", required = false) String userTelephone,
                                    @RequestParam(name = "comment") String comment, @RequestParam(name = "weight") int weight,HttpSession session)
    {
        String login=(String) session.getAttribute("login");

        Integer clientID=null;
        String unauthorizedClientContactInfo=null;
        boolean isOrderLimitReached;

        if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
            isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(date,userTelephone,"transport service");
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
            isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(date,clientID,"transport service");
        }

        if(isOrderLimitReached)
        {
            return "";
        }

        Services orderedService=servicesRepo.findByServicesID(serviceID);

        double cost=Math.round(0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice());
        /*0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice()*/;

       /* if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }*/

        Personnel assignedPersonnel=clientOrderAssigner.selectCargoTaxiOrderReceiver();

        ClientOrder newClientOrder=
                new ClientOrder(clientID,servicesRepo.findByServicesID(serviceID),assignedPersonnel,cost,"transport service",
                        date,time+":00","active",comment,unauthorizedClientContactInfo);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint,(double)weight,newClientOrder);
        order_routeRepo.save(newOrderRoute);

        return "redirect:/";
    }

    @PostMapping("/CalculateOrderTime")
    public String getTimeSelectionMenu(@RequestParam(name = "id") int serviceID, @RequestParam(name = "date") String date,
                                       @RequestParam(name = "telephone", required = false) String userTelephone,
                                       @RequestParam(name = "comment") String comment, HttpSession session, Map<String,Object> model)
    {
        String response;

        String login=(String) session.getAttribute("login");
        List<ClientOrder> listOfSelectedDateClientOrders = clientOrderRepo.findTimesOfAllActiveOrdersByDay(date,"customer service","active");
        Services orderedServiceDuration=servicesRepo.findByServicesID(serviceID);
        Iterable<ClientOrder> listOfPossibleOrderTime=
                possibleTimeCalcuator.countTimeGaps(listOfSelectedDateClientOrders, orderedServiceDuration.getServiceData().get(0).getCompletionTime(),servicesRepo);

        if(listOfPossibleOrderTime==null)
        {
            response="redirect:/ServicesPage?id="+serviceID;
        }
        else
        {
            Services currentService=servicesRepo.findByServicesID(serviceID);
            Customer_Services_Data additionalServiceData=
                    customer_services_dataRepo.findByMainServiceData(currentService);

            response="user/DateTimeChoosingPage";
            model.put("service", currentService);
            model.put("addServiceData", additionalServiceData);
            model.put("orderTimes", listOfPossibleOrderTime);
            model.put("currentOrderDate",date);
            model.put("comment", comment);

            if(login==null)
            {
                model.put("nonAuthorized",true);
                model.put("telephone",userTelephone);
            }
            else
            {
                model.put("nonAuthorized",false);
            }
        }

        //response="redirect:/ServicesPage?id="+serviceID;

        return response;
    }

    @PostMapping("/CreateOrder")
    public String addCustomerServiceOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "ordDate") String date,
                                          @RequestParam(name = "orderTime") String time, @RequestParam(name = "telephone", required = false) String userTelephone,
                                          @RequestParam(name = "comment") String comment, HttpSession session)
    {

        String login=(String) session.getAttribute("login");

        Integer clientID=null;
        String unauthorizedClientContactInfo=null;
        boolean isOrderLimitReached;

        if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
            isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(date,userTelephone,"transport service");
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
            isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(date,clientID,"transport service");
        }

        if(isOrderLimitReached)
        {
            return "";
        }

        Services orderedService=servicesRepo.findByServicesID(serviceID);

        double cost=orderedService.getPrice();

        /*if(login==null)
        {
            unauthorizedClientContactInfo=userTelephone;
        }
        else
        {
            Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
            clientID=currentRegisteredClient.getClientID();
        }*/

        Personnel assignedPersonnel=clientOrderAssigner.selectCustomerOrderReceiver();

        ClientOrder newClientOrder=new ClientOrder(clientID,orderedService,assignedPersonnel,cost,
                "customer service",date,time+":00","active",comment,unauthorizedClientContactInfo);

        clientOrderRepo.save(newClientOrder);


        return "redirect:/";
    }

}
