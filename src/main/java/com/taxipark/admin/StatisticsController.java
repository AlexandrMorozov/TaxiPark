package com.taxipark.admin;

import com.taxipark.dto.StatisticsDto;
import com.taxipark.repos.ClientOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.Session;
import java.util.List;

import java.util.Map;

@Controller
public class StatisticsController
{
    Session g;////

    @Autowired
    ClientOrderRepo clientOrderRepo;

   /* @GetMapping("/da")
    public void tst(Session session)
    {
        g=session;


        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "start_game")
                .add("player1", "ddf")
                .add("player2", "d")
                .add("turn", "Your turn: ")
                .build();

        try
        {
            g.getBasicRemote().sendText(addMessage.toString());
        }
        catch (IOException ex)
        {

        }


    }*/


    @GetMapping("/ffff")
    public String loadOrdersStatistics()
    {
        clientOrderRepo.getNumberOfServiceOrdersPerDate("Компьютерная диагностика автоэлектрики");

        return "";
    }

    @GetMapping("/fff1")
    public String loadPaymentsStatistics(Map<String,Object> model)
    {
        List<StatisticsDto> statistics=clientOrderRepo.getValueOfServiceOrdersPerDate("Компьютерная диагностика автоэлектрики");

        String[] coordinates=calculateStatistics(statistics,"12");

        model.put("xAxis",coordinates[0]);
        model.put("yAxis",coordinates[1]);

        /*for(int i=0;i<statistics.size();i++)
        {
           System.out.println( statistics.get(i).getDateOfOrder());
           System.out.println(statistics.get(i).getServiceName());
           System.out.println(statistics.get(i).getValue());
        }*/
        //model.put("orders",statistics);



        return "admin/Test";
    }

    private String[] calculateStatistics(List<StatisticsDto> statistics, String month)
    {
        String xAxisValues=new String();
        String yAxisValues=new String();

        for(int i=0;i<statistics.size();i++)
        {
            String[] date=statistics.get(i).getDateOfOrder().split("-");

            if(month.equals(date[1]))
            {
                xAxisValues=xAxisValues+","+date[2];
                yAxisValues=yAxisValues+","+statistics.get(i).getValue();
            }

        }

        System.out.println(xAxisValues);
        System.out.println(yAxisValues);

        String[] coordinates={xAxisValues,yAxisValues};

        return coordinates;
    }
}
