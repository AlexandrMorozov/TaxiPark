package com.taxipark;

import com.taxipark.config.SpringConfig;
import com.taxipark.dbmodel.*;
import com.taxipark.repos.*;
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

    @Autowired
    public DispatcherSocketHandler(ServicesRepo servicesRepo, ClientOrderRepo clientOrderRepo,
                                   Order_RouteRepo orderRouteRepo, PersonnelRepo personnelRepo,
                                   OrderLimitationChecker orderLimitationChecker, ClientsRepo clientsRepo,
                                   OrderCostCalculator orderCostCalculator)
    {
        this.servicesRepo=servicesRepo;
        this.clientOrderRepo=clientOrderRepo;
        this.orderRouteRepo=orderRouteRepo;
        this.personnelRepo=personnelRepo;
        this.orderLimitationChecker=orderLimitationChecker;
        this.clientsRepo=clientsRepo;
        this.orderCostCalculator=orderCostCalculator;
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
                                currentDate,currentTime,null,telephone,fromPoint,toPoint,null);

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
            else if("status_report".equals(jsonMessage.getString("action")))
            {
                int numOfCurrentOrders=Integer.parseInt(jsonMessage.get("numOfOrders").toString());
                listOfStates.put(jsonMessage.getString("sender"),numOfCurrentOrders);
            }
            else if("price_request".equals(jsonMessage.getString("action")))
            {
                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));
                double cauculatable=Double.parseDouble(jsonMessage.getString("unitCost"));
                double calculatedOrderCost=orderCostCalculator.calculateTaxiOrderCost(serviceID,cauculatable);

                JsonProvider provider = JsonProvider.provider();
                JsonObject responseMessage = provider.createObjectBuilder()
                        .add("action", "price_response")
                        .add("price", calculatedOrderCost)
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
                if(entry.getValue()==i)
                {
                    return entry;
                }
            }
        }

        return null;
    }

    private int saveTransportOrder(Integer clientID, Services orderedService, Personnel assignedEmployee,
                                    double cost, LocalDate dateOfOrder, LocalTime timeOfOrder,
                                    String comment, String unregUserInfo, String from, String to, Double cargo)
    {

        ClientOrder newClientOrder=new ClientOrder(clientID,orderedService,assignedEmployee,cost,
                "transport service", dateOfOrder.toString(), timeOfOrder.toString(),"active",comment,unregUserInfo);

        clientOrderRepo.save(newClientOrder);

        Order_Route newOrderRoute=new Order_Route(from,to,cargo,newClientOrder);
        orderRouteRepo.save(newOrderRoute);

        return newClientOrder.getOrderID();
    }

}
