package core;

import dbmodel.*;
import dto.CompletionTimeDto;
import logic.NavBarLoader;
import logic.PossibleTimeCalcuator;
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
    private Services_CategoryRepo services_categoryRepo;
    @Autowired
    private ServicesRepo servicesRepo;
    @Autowired
    private Customer_Services_DataRepo customer_services_dataRepo;
    @Autowired
    private ClientOrderRepo clientOrderRepo;
    @Autowired
    private Order_RouteRepo order_routeRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    private PossibleTimeCalcuator possibleTimeCalcuator=new PossibleTimeCalcuator();

    @PostMapping("/CreateTransportOrder")
    public String addTaxiOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "fromPoint") String fromPoint, @RequestParam(name = "toPoint") String toPoint,
                               @RequestParam(name = "name", required = false) String userName, @RequestParam(name = "telephone", required = false) String userTelephone,
                               @RequestParam(name = "comment") String comment, HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");


        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        Services orderedService=servicesRepo.findByServicesID(serviceID);

        Integer clientID=null;

        double cost=Math.round(orderedService.getCalculatablePrice()*(2+Math.random()*13));
        /*orderedService.getCalculatablePrice()*(2+Math.random()*13)*/

        LocalDate currentDate=LocalDate.now();
        LocalTime currentTime=LocalTime.now();

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint);
        order_routeRepo.save(newOrderRoute);

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
                new ClientOrder(clientID,serviceID,cost,"transport service",currentDate.toString(),currentTime.toString(),"active",comment,newOrderRoute.getRouteID());

        clientOrderRepo.save(newClientOrder);


        return "MainMenu";
    }

    @PostMapping("/CreateCargoTransportOrder")
    public String addCargoTaxiOrder(@RequestParam(name = "id") int serviceID, @RequestParam(name = "fromPoint") String fromPoint, @RequestParam(name = "toPoint") String toPoint,
                                    @RequestParam(name = "date") String date, @RequestParam(name = "time") String time, @RequestParam(name = "name", required = false) String userName,
                                    @RequestParam(name = "telephone", required = false) String userTelephone, @RequestParam(name = "comment") String comment,
                                    @RequestParam(name = "weight") int weight,HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        Services orderedService=servicesRepo.findByServicesID(serviceID);
        Integer clientID=null;
        double cost=Math.round(0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice());
        /*0.1*weight+(2+Math.random()*28)*orderedService.getCalculatablePrice()+orderedService.getPrice()*/;

        Order_Route newOrderRoute=new Order_Route(fromPoint,toPoint);
        order_routeRepo.save(newOrderRoute);

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
                new ClientOrder(clientID,serviceID,cost,"transport service",date,time+":00","active",comment,newOrderRoute.getRouteID());

        clientOrderRepo.save(newClientOrder);

        return "MainMenu";
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
        CompletionTimeDto orderedServiceDuration=servicesRepo.findCompletionTime(serviceID);
        Iterable<ClientOrder> listOfPossibleOrderTime=
                possibleTimeCalcuator.countTimeGaps(listOfSelectedDateClientOrders, orderedServiceDuration.getCompletionTime(),servicesRepo);

        if(listOfPossibleOrderTime==null)
        {
            response="";
        }
        else
        {
            Services currentService=servicesRepo.findByServicesID(serviceID);
            Customer_Services_Data additionalServiceData=
                    customer_services_dataRepo.findByCustomerServicesDataID(currentService.getCustomerServicesDataID());

            response="DateTimeChoosingPage";
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
                                          HttpSession session, Map<String,Object> model)
    {

        String login=(String) session.getAttribute("login");


        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

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

        ClientOrder newClientOrder=new ClientOrder(clientID,serviceID,cost,"customer service",date,time,"active",comment,null);

        clientOrderRepo.save(newClientOrder);


        return "MainMenu";
    }

}
