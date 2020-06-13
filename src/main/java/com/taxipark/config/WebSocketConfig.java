package com.taxipark.config;

import com.taxipark.StatisticsSocketHandler;
import com.taxipark.DispatcherSocketHandler;
import com.taxipark.repos.*;
import com.taxipark.services.OrderCostCalculator;
import com.taxipark.services.OrderLimitationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig
{
    @Autowired
    Payments_ListRepo paymentsListRepo;
    @Autowired
    Services_CategoryRepo servicesCategoryRepo;
    @Autowired
    ServicesRepo servicesRepo;
    @Autowired
    ClientOrderRepo clientOrderRepo;
    @Autowired
    Order_RouteRepo orderRouteRepo;
    @Autowired
    PersonnelRepo personnelRepo;
    @Autowired
    OrderLimitationChecker orderLimitationChecker;
    @Autowired
    ClientsRepo clientsRepo;
    @Autowired
    OrderCostCalculator orderCostCalculator;

    @Bean
    public StatisticsSocketHandler statisticsSocketHandler()
    {
        System.out.println(paymentsListRepo);
        return new StatisticsSocketHandler(paymentsListRepo,servicesCategoryRepo);
    }

    @Bean
    public DispatcherSocketHandler DispatcherSocketHandler()
    {
        System.out.println(servicesRepo);
        return new DispatcherSocketHandler(servicesRepo,clientOrderRepo,orderRouteRepo,personnelRepo,
                orderLimitationChecker,clientsRepo,orderCostCalculator);
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
