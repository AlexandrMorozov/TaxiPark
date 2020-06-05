package com.taxipark.config;

import com.taxipark.StatisticsSocketHandler;
import com.taxipark.repos.Payments_ListRepo;
import com.taxipark.repos.Services_CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
/*@EnableWebSocket*/
public class WebSocketConfig
{
    @Autowired
    Payments_ListRepo paymentsListRepo;
    @Autowired
    Services_CategoryRepo servicesCategoryRepo;

    @Bean
    public StatisticsSocketHandler statisticsSocketHandler()
    {
        System.out.println(paymentsListRepo);
        return new StatisticsSocketHandler(paymentsListRepo,servicesCategoryRepo);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        System.out.println("serverEndPointExporter");
        return new ServerEndpointExporter();
    }

    @Bean
    public SpringConfig customSpringConfigurator()
    {
        return new SpringConfig();
    }

}
