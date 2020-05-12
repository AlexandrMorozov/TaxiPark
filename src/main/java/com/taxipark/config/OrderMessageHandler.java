package com.taxipark.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderMessageHandler extends TextWebSocketHandler
{


    //org.springframework.web.socket.handler
    //private LinkedHashMap<String, WebSocketSession> listOfWebSocketSessions=new LinkedHashMap<>();

    private List<WebSocketSession> establishedSessions = new CopyOnWriteArrayList<>();


   // private List<WebSocketSession> listOfEstablishedSocketSessions=new CopyOnWriteArrayList<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session)
    {


        establishedSessions.add(session);

        System.out.println(establishedSessions.size());

        //System.out.println(session.getAttributes().get("objectID").toString());

        //listOfWebSocketSessions.put(session.getAttributes().get("objectID").toString(),session);


        //listOfEstablishedSocketSessions.add(session);
        //super.afterConnectionEstablished(session);


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    {
        System.out.println("remove " + session.getId());
        establishedSessions.remove(session);
        //session.getAttributes().get("objectID");
        //System.out.println(session.getAttributes().get("objectID").toString());

       // listOfWebSocketSessions.remove(session.getAttributes().get("objectID").toString());


        //listOfEstablishedSocketSessions.remove(session);
        //super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
    {
       // System.out.println("handl");

        //message.getPayload()
       // super.handleTextMessage(session, message);
    }


    public void sendMessageToEmployee(String message,WebSocketSession session)
    {
        TextMessage finalMessage=new TextMessage(message);

        try
        {
            session.sendMessage(finalMessage);
        }
        catch (IOException ex)
        {
            System.out.println("fail");
        }

    }

    public WebSocketSession findAvailableTaxi()
    {
        System.out.println(establishedSessions.size());

        int availableTaxiNum=(int) (Math.random()*(establishedSessions.size()-1));

        return establishedSessions.get(availableTaxiNum);

    }
}
