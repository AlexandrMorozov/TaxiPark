package com.taxipark;

import com.taxipark.config.SpringConfig;
import com.taxipark.dbmodel.*;
import com.taxipark.repos.*;
import com.taxipark.services.ClientOrderAssigner;
import com.taxipark.services.OrderCostCalculator;
import com.taxipark.services.OrderLimitationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/order/{login}/{type}", configurator = SpringConfig.class)
public class DispatcherSocketHandler
{
    ServicesRepo servicesRepo;
    ClientOrderRepo clientOrderRepo;
    Order_RouteRepo orderRouteRepo;
    PersonnelRepo personnelRepo;
    OrderLimitationChecker orderLimitationChecker;
    ClientsRepo clientsRepo;
    OrderCostCalculator orderCostCalculator;
    ClientOrderAssigner clientOrderAssigner;
    TransportRepo transportRepo;

    @Autowired
    public DispatcherSocketHandler(ServicesRepo servicesRepo, ClientOrderRepo clientOrderRepo,
                                   Order_RouteRepo orderRouteRepo, PersonnelRepo personnelRepo,
                                   OrderLimitationChecker orderLimitationChecker, ClientsRepo clientsRepo,
                                   OrderCostCalculator orderCostCalculator, ClientOrderAssigner clientOrderAssigner,
                                   TransportRepo transportRepo)
    {
        this.servicesRepo=servicesRepo;
        this.clientOrderRepo=clientOrderRepo;
        this.orderRouteRepo=orderRouteRepo;
        this.personnelRepo=personnelRepo;
        this.orderLimitationChecker=orderLimitationChecker;
        this.clientsRepo=clientsRepo;
        this.orderCostCalculator=orderCostCalculator;
        this.clientOrderAssigner=clientOrderAssigner;
        this.transportRepo=transportRepo;
    }



    private HashMap<String,Session> listOfConnections=new HashMap<>();
    private HashMap<String,Integer> listOfStates=new HashMap<>();

    @OnOpen
    public void openDispatcherConnection(Session session ,@PathParam("login") String login,@PathParam("type") String type)
    {
        if(!type.equals("customer"))
        {
            System.out.println(login);
            listOfConnections.put(login,session);
        }
    }

    @OnClose
    public void closeDispatcherConnection(Session session,@PathParam("login") String login,@PathParam("type") String type)
    {
        System.out.println(session+" is closed");

        if(!type.equals("customer"))
        {
            listOfStates.remove(login);
            listOfConnections.remove(login);
        }

    }

    @OnError
    public void handleDispatcherConnection(Throwable error)
    {
        System.out.println(error);
    }

    @OnMessage
    public void handleDispatcherMessage(String message, Session session) throws IOException
    {
        try (JsonReader reader = Json.createReader(new StringReader(message)))
        {
            JsonObject jsonMessage = reader.readObject();

            if("taxi_order_request".equals(jsonMessage.getString("action")))
            {
                boolean isOrderLimitReached;

                String telephone;
                String login;
                Integer clientID;

                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));
                String fromPoint=jsonMessage.getString("fromPoint");
                String toPoint=jsonMessage.getString("toPoint");
                double cost=Double.parseDouble(jsonMessage.getString("cost"));

                LocalDate currentDate=LocalDate.now();
                LocalTime currentTime=LocalTime.now();

                if(jsonMessage.isNull("login"))
                {
                    clientID=null;
                    telephone=jsonMessage.getString("telephone");
                    isOrderLimitReached=orderLimitationChecker.checkInstantOrderLimit(telephone,"transport service");
                }
                else
                {
                    telephone=null;
                    login=jsonMessage.getString("login");
                    Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
                    clientID=currentRegisteredClient.getClientID();
                    isOrderLimitReached=orderLimitationChecker.checkInstantOrderLimit(clientID,"transport service");
                }

                if(isOrderLimitReached)
                {
                    JsonProvider provider = JsonProvider.provider();

                    JsonObject responseMessage=provider.createObjectBuilder()
                            .add("action","taxi_order_answer")
                            .add("response", false)
                            .add("comment","Вы не можете сделать заказ, при наличии исполняемого заказа подобного типа")
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());

                }
                else
                {
                    JsonProvider provider = JsonProvider.provider();

                    boolean isOrderAccepted;
                    String comment;
                    int orderID=0;

                    Map.Entry<String,Integer> receiver=selectOrderReceiver();

                    if(receiver!=null)
                    {
                        isOrderAccepted=true;
                        comment="Поздравляем! Ваш заказ успешно принят. Ожидайте автомобиля.";

                        Services orderedService=servicesRepo.findByServicesID(serviceID);
                        Personnel assignedEmployee=personnelRepo.findByLogin(receiver.getKey());

                        orderID=saveTransportOrder(clientID,orderedService,assignedEmployee,cost,
                                currentDate.toString(),currentTime.toString(),null,telephone,fromPoint,toPoint,null);

                        JsonObject employeeMessage=provider.createObjectBuilder()
                                .add("action","new_order_taxi")
                                .add("serviceName", servicesRepo.findByServicesID(serviceID).getServiceName())
                                .add("orderID",orderID)
                                .add("date",currentDate.toString())
                                .add("time",currentTime.toString())
                                .add("price",cost)
                                .add("pod",fromPoint)
                                .add("poa",toPoint)
                                .build();

                        listOfConnections.get(receiver.getKey()).getBasicRemote().sendText(employeeMessage.toString());
                    }
                    else
                    {
                        isOrderAccepted=false;
                        comment="К сожалению сейчас мы не можем принять ваш заказ по причине отсутствия машин(";
                    }


                    JsonObject responseMessage = provider.createObjectBuilder()
                            .add("action", "taxi_order_answer")
                            .add("response", isOrderAccepted)
                            .add("comment",comment)
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());
                }

            }
            else if("cargo_order_request".equals(jsonMessage.getString("action")))
            {
                boolean isOrderLimitReached;

                String telephone;
                String login;
                Integer clientID;

                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));
                String fromPoint=jsonMessage.getString("fromPoint");
                String toPoint=jsonMessage.getString("toPoint");
                double cost=Double.parseDouble(jsonMessage.getString("cost"));
                double weight=Double.parseDouble(jsonMessage.getString("weight"));
                String commen=jsonMessage.getString("comment");

                String currentDate=jsonMessage.getString("date");
                String currentTime=jsonMessage.getString("time");

                if(jsonMessage.isNull("login"))
                {
                    clientID=null;
                    telephone=jsonMessage.getString("telephone");
                    isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(currentDate,telephone,"transport service");

                }
                else
                {
                    telephone=null;
                    login=jsonMessage.getString("login");
                    Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
                    clientID=currentRegisteredClient.getClientID();
                    isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(currentDate,clientID,"transport service");
                }

                if(isOrderLimitReached)
                {
                    JsonProvider provider = JsonProvider.provider();

                    JsonObject responseMessage=provider.createObjectBuilder()
                            .add("action","taxi_order_answer")
                            .add("response", false)
                            .add("comment","Вы не можете сделать заказ, при наличии исполняемого заказа подобного типа")
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());

                }
                else
                {
                    JsonProvider provider = JsonProvider.provider();

                    boolean isOrderAccepted;
                    String comment;
                    int orderID=0;

                    Personnel assignedEmployee=clientOrderAssigner.selectCargoTaxiOrderReceiver();
                    Services orderedService=servicesRepo.findByServicesID(serviceID);

                    if(assignedEmployee==null)
                    {
                        isOrderAccepted=false;
                        comment="К сожалению сейчас мы не можем принять ваш заказ по причине отсутствия машин(";
                    }
                    else
                    {
                        isOrderAccepted=true;
                        comment="Поздравляем! Ваш заказ успешно принят";

                        orderID=saveTransportOrder(clientID,orderedService,assignedEmployee,cost,
                                currentDate,currentTime+":00",commen,telephone,fromPoint,toPoint,weight);

                        if(listOfConnections.get(assignedEmployee.getLogin())!=null)
                        {
                            JsonObject employeeMessage=provider.createObjectBuilder()
                                    .add("action","new_order_cargo")
                                    .add("serviceName", servicesRepo.findByServicesID(serviceID).getServiceName())
                                    .add("orderID",orderID)
                                    .add("date",currentDate.toString())
                                    .add("time",currentTime.toString())
                                    .add("price",cost)
                                    .add("pod",fromPoint)
                                    .add("poa",toPoint)
                                    .add("wgt",weight)
                                    .build();

                            listOfConnections.get(assignedEmployee.getLogin()).getBasicRemote().sendText(employeeMessage.toString());
                        }
                    }

                    JsonObject responseMessage = provider.createObjectBuilder()
                            .add("action", "taxi_order_answer")
                            .add("response", isOrderAccepted)
                            .add("comment",comment)
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());
                }
            }
            else if("service_order_request".equals(jsonMessage.getString("action")))
            {
                boolean isOrderLimitReached;

                String telephone;
                String login;
                Integer clientID;

                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));
                double cost=servicesRepo.findByServicesID(serviceID).getPrice();

                System.out.println("sdfvd");

                String commen=jsonMessage.getString("comment");

                String currentDate=jsonMessage.getString("date");
                String currentTime=jsonMessage.getString("time");

                System.out.println("111111");

                if(jsonMessage.isNull("login"))
                {
                    System.out.println("qa");

                    clientID=null;
                    telephone=jsonMessage.getString("telephone");

                    System.out.println("qas");
                    isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(currentDate,telephone,"customer service");

                }
                else
                {
                    System.out.println("345");

                    telephone=null;
                    login=jsonMessage.getString("login");
                    Clients currentRegisteredClient=clientsRepo.findByClientLogin(login);
                    clientID=currentRegisteredClient.getClientID();
                    isOrderLimitReached=orderLimitationChecker.checkPreOrderLimit(currentDate,clientID,"customer service");
                }

                if(isOrderLimitReached)
                {
                    System.out.println("34dfsfssf5");

                    JsonProvider provider = JsonProvider.provider();

                    JsonObject responseMessage=provider.createObjectBuilder()
                            .add("action","taxi_order_answer")
                            .add("response", false)
                            .add("comment","Вы не можете заказать услугу, т.к превысили лимит заказов на неделю")
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());

                }
                else
                {
                    JsonProvider provider = JsonProvider.provider();

                    boolean isOrderAccepted;
                    String comment;
                    int orderID=0;

                    System.out.println("zcv");

                    Personnel assignedEmployee=clientOrderAssigner.selectCustomerOrderReceiver();
                    Services orderedService=servicesRepo.findByServicesID(serviceID);

                    System.out.println("az");

                    if(assignedEmployee==null)
                    {
                        isOrderAccepted=false;
                        comment="К сожалению сейчас мы не можем принять ваш заказ по причине работников(";

                        System.out.println("azxcz");
                    }
                    else
                    {
                        System.out.println("cvb");

                        isOrderAccepted=true;
                        comment="Поздравляем! Ваш заказ успешно принят";

                        orderID=saveCustomerOrder(clientID,orderedService,assignedEmployee,cost,
                                currentDate,currentTime,commen,telephone);

                        if(listOfConnections.get(assignedEmployee.getLogin())!=null)
                        {
                            JsonObject employeeMessage=provider.createObjectBuilder()
                                    .add("action","new_order_cargo")
                                    .add("serviceName", servicesRepo.findByServicesID(serviceID).getServiceName())
                                    .add("orderID",orderID)
                                    .add("date",currentDate.toString())
                                    .add("time",currentTime.toString())
                                    .add("price",cost)
                                    .add("comment",commen)
                                    .build();

                            listOfConnections.get(assignedEmployee.getLogin()).getBasicRemote().sendText(employeeMessage.toString());
                        }
                    }

                    JsonObject responseMessage = provider.createObjectBuilder()
                            .add("action", "taxi_order_answer")
                            .add("response", isOrderAccepted)
                            .add("comment",comment)
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());
                }
            }
            else if("status_report".equals(jsonMessage.getString("action")))
            {
                int numOfCurrentOrders=Integer.parseInt(jsonMessage.get("numOfOrders").toString());
                listOfStates.put(jsonMessage.getString("sender"),numOfCurrentOrders);
            }
            else if("price_request".equals(jsonMessage.getString("action")))
            {
                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));
                double cauculatable=Double.parseDouble(jsonMessage.getString("unitCost"));
                double calculatedOrderCost=0;

                if(jsonMessage.getString("type").equals("pas"))
                {
                    calculatedOrderCost=orderCostCalculator.calculateTaxiOrderCost(serviceID,cauculatable);
                }
                else if(jsonMessage.getString("type").equals("car"))
                {
                    double weight=Double.parseDouble(jsonMessage.getString("weight"));
                    calculatedOrderCost=orderCostCalculator.calculateCargoTaxiOrderCost(/*,*/cauculatable,weight);
                }

                //double calculatedOrderCost=orderCostCalculator.calculateTaxiOrderCost(serviceID,cauculatable);

                JsonProvider provider = JsonProvider.provider();
                JsonObject responseMessage = provider.createObjectBuilder()
                        .add("action", "price_response")
                        .add("price", calculatedOrderCost)
                        .add("type", jsonMessage.getString("type"))
                        .build();

                session.getBasicRemote().sendText(responseMessage.toString());
            }
        }


    }

    @Scheduled(fixedRate = 10000)
    private void updatePersonnelStatus()
    {
        System.out.println(listOfConnections.size());

        for(Session value : listOfConnections.values())
        {
            JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                    .add("action", "check_status")
                    .build();
            try
            {
                value.getBasicRemote().sendText(addMessage.toString());
            }
            catch (IOException ex)
            {
                System.out.println(ex);
            }

        }

    }

    private Map.Entry<String, Integer> selectOrderReceiver()
    {
        for (int i=0;i<4;i++)
        {
            for(Map.Entry<String, Integer> entry : listOfStates.entrySet())
            {
                Personnel currentPersonnel=personnelRepo.findByLogin(entry.getKey());

                if(entry.getValue()==i /*&&*/
                        /*currentPersonnel.getPersonnelProfession().getPositionName().equals("Водитель")
                && transportRepo.findByTransportID(currentPersonnel.getTransportID()).getType().equals("Пассажирский")*/)
                {
                    return entry;
                }
            }
        }

        return null;
    }

    private int saveTransportOrder(Integer clientID, Services orderedService, Personnel assignedEmployee,
                                    double cost, String dateOfOrder, String timeOfOrder,
                                    String comment, String unregUserInfo, String from, String to, Double cargo)
    {

        ClientOrder newClientOrder=new ClientOrder(clientID,orderedService,assignedEmployee,cost,
                "transport service", dateOfOrder, timeOfOrder,"active",comment,unregUserInfo);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(from,to,cargo,newClientOrder);
        orderRouteRepo.save(newOrderRoute);

        return newClientOrder.getOrderID();
    }

    private int saveCustomerOrder(Integer clientID, Services orderedService, Personnel assignedEmployee, double cost,
                              String dateOfOrder, String timeOfOrder, String comment, String unregUserInfo)
    {
        ClientOrder newClientOrder=new ClientOrder(clientID,orderedService,assignedEmployee,cost,
                "customer service",dateOfOrder,timeOfOrder+":00","active",comment,unregUserInfo);

        clientOrderRepo.save(newClientOrder);

        return newClientOrder.getOrderID();
    }


}
