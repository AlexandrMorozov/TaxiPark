package com.taxipark;

import com.taxipark.dbmodel.Clients;
import com.taxipark.dbmodel.Route;
import com.taxipark.dbmodel.Route_Points;
import com.taxipark.dto.StatisticsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackageClasses= {Route_Points.class,Route.class, Clients.class})
//@EntityScan(basePackages = {"com.taxipark.dto.*","com.taxipark.dbmodel.*"})
//@EntityScan(basePackageClasses={StatisticsDto.class,Route_Points.class,Route.class, Clients.class})
//@EnableScheduling
//@EnableScheduling
public class Application
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}