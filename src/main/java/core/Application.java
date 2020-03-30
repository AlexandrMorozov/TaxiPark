package core;

import dbmodel.Clients;
import dbmodel.Route;
import dbmodel.Route_Points;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackageClasses= {Route_Points.class,Route.class, Clients.class})
public class Application
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}