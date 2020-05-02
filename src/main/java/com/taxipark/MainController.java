package com.taxipark;

import com.taxipark.dbmodel.Customer_Services_Data;
import com.taxipark.dbmodel.Services;
import com.taxipark.logic.NavBarLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.taxipark.repos.Customer_Services_DataRepo;
import com.taxipark.repos.ServicesRepo;
import com.taxipark.repos.Services_CategoryRepo;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class MainController
{
    @Autowired
    private Services_CategoryRepo services_categoryRepo;
    @Autowired
    private ServicesRepo servicesRepo;
    @Autowired
    private Customer_Services_DataRepo customer_services_dataRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    @GetMapping
    public String mainPage(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        return "user/MainMenu";
    }

    @GetMapping("/CategoriesMenu")
    public String categoriesMenu(HttpSession session,Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        return "user/CategoriesMenu";
    }

    @GetMapping("/SubcategoriesMenu")
    public String subcategoriesMenu(@RequestParam(name = "id") int categoryID, HttpSession session,Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);

        Iterable<Services> listOfServices=servicesRepo.findAllByCategoryID(categoryID);

        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        model.put("subcategories", listOfServices);

        return "user/SubcategoriesMenu";
    }

    @GetMapping("/ServicesPage")
    public String servicePage(@RequestParam(name = "id") int serviceID, HttpSession session,Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        if(login==null)
        {
            model.put("nonAuthorized",true);
        }
        else
        {
            model.put("nonAuthorized",false);
        }


        Services currentService=servicesRepo.findByServicesID(serviceID);
        Customer_Services_Data additionalServiceData=
                customer_services_dataRepo.findByServiceID(currentService.getServicesID());

        model.put("service", currentService);
        model.put("addServiceData", additionalServiceData);

        LocalDate currentDate=LocalDate.now();
        LocalDate weekLaterDate=currentDate.plusDays(7);

        model.put("currentDate",currentDate);
        model.put("weekLaterDate",weekLaterDate);

        return "user/ServicesPage";
    }

    @GetMapping("/transportPage")
    public String transportPage(@RequestParam(name = "id", required = true) int serviceID, @RequestParam(name="name") String serviceName,
                                HttpSession session, Map<String,Object> model)
    {

        String response;
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        if(login==null)
        {
            model.put("nonAuthorized",true);
        }
        else
        {
            model.put("nonAuthorized",false);
        }

        Services currentService=servicesRepo.findByServicesID(serviceID);

        model.put("service", currentService);

        if(serviceName.equals("Заказ такси"))
        {
            response="user/TaxiPage";
        }
        else
        {
            LocalDate currentDate=LocalDate.now();
            LocalDate weekLaterDate=currentDate.plusDays(7);

            model.put("currentDate",currentDate);
            model.put("weekLaterDate",weekLaterDate);

            response="user/CargoTaxiPage";
        }

        return response;
    }

}

