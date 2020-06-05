package com.taxipark;

import com.taxipark.repos.ServicesRepo;
import com.taxipark.services.OrderCostCalculator;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;


@Component
@ServerEndpoint("/order/{login}")
public class WebSocketHandler
{

    OrderCostCalculator orderCostCalculator=new OrderCostCalculator();

    private static WebSocketHandler instance;

    public static synchronized WebSocketHandler getInstance()
    {
        if(instance==null)
        {
            instance=new WebSocketHandler();
        }

        return instance;
    }

    private static HashMap<String,Boolean> listOfStates=new HashMap<>();
    private static HashMap<String,Session> listOfConnections=new HashMap<>();

    @OnOpen
    public void open(Session session,@PathParam("login") String login)
    {
        listOfConnections.put(login,session);
    }

    @OnClose
    public void close(Session session)
    {
        System.out.println("dsdfdgdg");
    }

    @OnError
    public void onError(Throwable error)
    {
        System.out.println(error.toString());
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException
    {
        try (JsonReader reader = Json.createReader(new StringReader(message)))
        {

            JsonObject jsonMessage = reader.readObject();

            if("price_request".equals(jsonMessage.getString("action")))
            {
                int serviceID=Integer.parseInt(jsonMessage.getString("serviceID"));

                double cauculatable=Double.parseDouble(jsonMessage.getString("unitCost"));

                System.out.println("qqqq");

                System.out.println(orderCostCalculator);

                double calculatedOrderCost=orderCostCalculator.calculateTaxiOrderCost(serviceID,cauculatable);

                System.out.println("11111");

                JsonProvider provider = JsonProvider.provider();
                JsonObject responseMessage = provider.createObjectBuilder()
                        .add("action", "price_response")
                        .add("price", calculatedOrderCost)
                        .build();

                session.getBasicRemote().sendText(responseMessage.toString());
            }
            else if ("status_report".equals(jsonMessage.getString("action")))
            {
                Boolean status=Boolean.parseBoolean(jsonMessage.getString("status"));
                listOfStates.put(jsonMessage.getString("sender"),status);
            }
            else if("order_request".equals(jsonMessage.getString("action")))
            {
                boolean isOrderAccepted;

                JsonProvider provider = JsonProvider.provider();
                JsonObject addMessage = provider.createObjectBuilder()
                        .add("action", "check_status")
                        .build();

                isOrderAccepted=assignOrder(addMessage,jsonMessage.getString("sender"),true);

                if(!isOrderAccepted)
                {
                    assignOrder(addMessage,jsonMessage.getString("sender"),false);
                }
            }

        }
    }

    private boolean assignOrder(JsonObject outputMessage, String sender, boolean neededState)
    {
        boolean isOrderAccepted=false;

        for(Boolean value : listOfStates.values())
        {
            if(value==neededState)
            {
                try
                {
                    listOfConnections.get(sender).getBasicRemote().sendText(outputMessage.toString());
                    listOfStates.put(sender,false);

                    isOrderAccepted=true;

                    break;
                }
                catch (IOException ex)
                {

                }
            }

        }

        return isOrderAccepted;
    }

   /* @Scheduled(fixedRate = 5000)
    private void updatePersonnelStatus()
    {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(listOfConnections.size());

        for(Session value : listOfConnections.values())
        {
            System.out.println("vvv");

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

            }


        }
    }*/


    public void assignTaxiOrder(String sender)
    {
        boolean isOrderAccepted;

        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "check_status")
                .build();

        System.out.println("henlooooooooooooooooooooooo");
        System.out.println(listOfConnections.size());

        isOrderAccepted=assignOrder(addMessage,sender,true);

        if(!isOrderAccepted)
        {
            assignOrder(addMessage,sender,false);
        }
    }
}
