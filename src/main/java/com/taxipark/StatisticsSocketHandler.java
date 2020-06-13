package com.taxipark;

import com.taxipark.config.SpringConfig;
import com.taxipark.dbmodel.Payments_List;
import com.taxipark.dbmodel.Services_Category;
import com.taxipark.dto.StatisticsDto;
import com.taxipark.repos.Payments_ListRepo;
import com.taxipark.repos.Services_CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.*;
import javax.json.spi.JsonProvider;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@ServerEndpoint(value = "/statistics", configurator = SpringConfig.class)
public class StatisticsSocketHandler {

    Payments_ListRepo paymentsListRepo;
    Services_CategoryRepo servicesCategoryRepo;

    @Autowired
    public StatisticsSocketHandler(Payments_ListRepo paymentsListRepo, Services_CategoryRepo servicesCategoryRepo)
    {
        this.paymentsListRepo = paymentsListRepo;
        this.servicesCategoryRepo=servicesCategoryRepo;

    }

    public StatisticsSocketHandler()
    {

    }

    @OnOpen
    public void openStatisticsConnection(Session session)
    {
    }

    @OnClose
    public void closeStatisticsConnection(Session session)
    {

    }

    @OnError
    public void handleStatisticsConnection(Throwable error)
    {
        System.out.println(error.toString());
    }

    @OnMessage
    public void handleStatisticsConnectionMessage(String message, Session session) throws IOException
    {
        try (JsonReader reader = Json.createReader(new StringReader(message)))
            {


                JsonObject jsonMessage = reader.readObject();

                List<StatisticsDto> f1=null;
                List<StatisticsDto> f2=null;


                if(!"all_years".equals(jsonMessage.getString("action")) &&
                    !"all_categories".equals(jsonMessage.getString("action")))
            {
                String period=jsonMessage.getString("period");
                String from=jsonMessage.getString("from");
                String to=jsonMessage.getString("to");

                System.out.println(period);
                System.out.println(from);
                System.out.println(to);

                if("all_orders".equals(jsonMessage.getString("action")))
                {

                    if(period.equals("year"))
                    {
                        f1=paymentsListRepo.findNumberOfAllPaymentsByMonths("income",from,to);
                        f2=paymentsListRepo.findNumberOfAllPaymentsByMonths("expense",from,to);
                    }
                    else
                    {
                        System.out.println("111");
                        f1=paymentsListRepo.findNumberOfAllPaymentsByDays("income",from,to);
                        f2=paymentsListRepo.findNumberOfAllPaymentsByDays("expense",from,to);

                        System.out.println(f1.size());
                    }
                }
                else if("all_payments".equals(jsonMessage.getString("action")))
                {
                    if(period.equals("year"))
                    {
                        f1=paymentsListRepo.findSumOfAllPaymentsByMonths("income",from,to);
                        f2=paymentsListRepo.findSumOfAllPaymentsByMonths("expense",from,to);
                    }
                    else
                    {
                        f1=paymentsListRepo.findSumOfAllPaymentsByDays("income",from,to);
                        f2=paymentsListRepo.findSumOfAllPaymentsByDays("expense",from,to);
                    }

                }
                else if("orders_by_category".equals(jsonMessage.getString("action")))
                {
                    String category=jsonMessage.getString("target");

                    if(period.equals("year"))
                    {
                        f1=paymentsListRepo.findNumberOfPaymentsByServiceCategoryAndMonths("income",category,from,to);
                        f2=paymentsListRepo.findNumberOfPaymentsByServiceCategoryAndMonths("expense",category,from,to);
                    }
                    else
                    {
                        f1=paymentsListRepo.findNumberOfPaymentsByServiceCategoryAndDays("income",category,from,to);
                        f2=paymentsListRepo.findNumberOfPaymentsByServiceCategoryAndDays("expense",category,from,to);
                    }


                }
                else if ("payments_by_category".equals(jsonMessage.getString("action")))
                {
                    String category=jsonMessage.getString("target");

                    if(period.equals("year"))
                    {
                        f1=paymentsListRepo.findSumOfPaymentsByServiceCategoryAndMonths("income",category,from,to);
                        f2=paymentsListRepo.findSumOfPaymentsByServiceCategoryAndMonths("expense",category,from,to);
                    }
                    else
                    {
                        f1=paymentsListRepo.findSumOfPaymentsByServiceCategoryAndDays("income",category,from,to);
                        f2=paymentsListRepo.findSumOfPaymentsByServiceCategoryAndDays("expense",category,from,to);
                    }

                }

                JsonProvider provider = JsonProvider.provider();
                JsonObject responseMessage = provider.createObjectBuilder()
                        .add("action", "response")
                        .add("f1", buildCoordinatesMessageData(f1).build())
                        .add("f2", buildCoordinatesMessageData(f2).build())
                        .build();

                session.getBasicRemote().sendText(responseMessage.toString());
            }
            else
            {
                System.out.println("step1");
                JsonProvider provider = JsonProvider.provider();

                if("all_years".equals(jsonMessage.getString("action")))
                {
                    System.out.println("step2");

                    JsonObject responseMessage = provider.createObjectBuilder()
                            .add("action", "year_response")
                            .add("years", buildYearMessageData(paymentsListRepo.findAllYears()).build())
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());
                }
                else if("all_categories".equals(jsonMessage.getString("action")))
                {
                    System.out.println("step3");
                    JsonObject responseMessage = provider.createObjectBuilder()
                            .add("action", "category_response")
                            .add("categories", buildCategoryMessageData(servicesCategoryRepo.findAllCategories()).build())
                            .build();

                    session.getBasicRemote().sendText(responseMessage.toString());
                }

            }


           /* else if("orders_by_service".equals(jsonMessage.getString("action")))
            {
                String service=jsonMessage.getString("target");

                if(period.equals("year"))
                {
                    f1=paymentsListRepo.findSumOfPaymentsByServiceAndMonths("income",service,from,to);
                    f2=paymentsListRepo.findSumOfPaymentsByServiceAndMonths("expense",service,from,to);
                }
                else
                {
                    f1=paymentsListRepo.findSumOfPaymentsByServiceAndDays("income",service,from,to);
                    f2=paymentsListRepo.findSumOfPaymentsByServiceAndDays("expense",service,from,to);
                }

            }*/
           /* else if ("payments_by_service".equals(jsonMessage.getString("action")))
            {
                String service=jsonMessage.getString("target");

                if(period.equals("year"))
                {
                    f1=paymentsListRepo.findNumberOfPaymentsByServiceAndMonths("income",service,from,to);
                    f2=paymentsListRepo.findNumberOfPaymentsByServiceAndMonths("expense",service,from,to);
                }
                else
                {
                    f1=paymentsListRepo.findNumberOfPaymentsByServiceAndDays("income",service,from,to);
                    f2=paymentsListRepo.findNumberOfPaymentsByServiceAndDays("expense",service,from,to);
                }

            }*/


        }
    }

    private JsonArrayBuilder buildCoordinatesMessageData(List<StatisticsDto> messageData)
    {
        JsonArrayBuilder messageBuilder=Json.createArrayBuilder();

        for(int i=0;i<messageData.size();i++)
        {
            String date=messageData.get(i).getDate();
            double value=messageData.get(i).getValue();

            JsonObjectBuilder coordinate=Json.createObjectBuilder();
            messageBuilder.add(coordinate
                    .add("date",date)
                    .add("value",value)
                    .build());
        }

        return messageBuilder;
    }

    private JsonArrayBuilder buildYearMessageData(List<Payments_List> messageData)
    {
        JsonArrayBuilder messageBuilder=Json.createArrayBuilder();

        for(int i=0;i<messageData.size();i++)
        {
            int year=messageData.get(i).getPaymentYear();

            JsonObjectBuilder yearContiner=Json.createObjectBuilder();
            messageBuilder.add(yearContiner
                    .add("year",year)
                    .build());
        }

        return messageBuilder;
    }

    private JsonArrayBuilder buildCategoryMessageData(List<Services_Category> messageData)
    {
        JsonArrayBuilder messageBuilder=Json.createArrayBuilder();

        for(int i=0;i<messageData.size();i++)
        {
            String categoryName=messageData.get(i).getServiceCategoryName();

            JsonObjectBuilder yearContiner=Json.createObjectBuilder();
            messageBuilder.add(yearContiner
                    .add("categoryName",categoryName)
                    .build());
        }

        return messageBuilder;
    }

}
