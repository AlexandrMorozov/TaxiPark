package com.taxipark.services;

import com.taxipark.repos.PersonnelRepo;
import com.taxipark.repos.ServicesRepo;
import com.taxipark.repos.Services_CategoryRepo;
import com.taxipark.dbmodel.Services;
import com.taxipark.dbmodel.Services_Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NavBarLoader
{
    public void checkAuthorization(String login, Map<String,Object> model)
    {
        if(login==null)
        {
            model.put("authEntLink","/Registration.mustache");
            model.put("authEntLinkName","Войти");
        }
        else
        {
            model.put("authEntLink","/UserAccount?log="+login);
            model.put("authEntLinkName",login);
        }


    }

    public boolean checkAuthorizationAdmin(String login, Map<String,Object> model)
    {
        boolean isAuthorized=true;

        if(login==null)
        {
            isAuthorized=false;
        }
        else
        {
            model.put("employeeName",login);
        }

        return isAuthorized;
    }

    public boolean checkRoleAdmin(Map<String,Object> model, PersonnelRepo personnelRepo,String login)
    {
        boolean isAdmin;

        if(!personnelRepo.findByLogin(login).getPersonnelProfession().getPositionName().equals("Администратор"))
        {
            model.put("isAdmin",false);
            model.put("isntAdmin",true);
            model.put("cabLink","/adminportal/employeepage?log="+login);
            //return "admin/adminuser/EmployeeAccount?log="+login;

            isAdmin=false;
            /*return true;*/
        }
        else
        {
            model.put("isAdmin",true);
            model.put("isntAdmin",false);
            model.put("statLink","/adminportal/statistics");
            model.put("perLink","/adminportal/personnel");
            model.put("tranLink","/adminportal/transport");
            model.put("catLink","/adminportal/categories");

            isAdmin=true;
        }

        return isAdmin;
    }

    public void loadNavigationBarLinks(Map<String, Object> model, Services_CategoryRepo services_categoryRepo, ServicesRepo servicesRepo)
    {
        Iterable<Services_Category> listOfServiceCategories=
                services_categoryRepo.findAllByServiceCategoryType("customer service");

        List<Services_Category> listOfTransportCategories=services_categoryRepo.findAllByServiceCategoryType("transport service");
        List<Services> listOfTransportServices=new ArrayList<>();

        for(int i=0;i<listOfTransportCategories.size();i++)
        {
            listOfTransportServices.addAll(servicesRepo.findAllByCategoryID(listOfTransportCategories.get(i).getServiceCategoryID()));
        }

        Iterable<Services> finalListOfTransportServices=listOfTransportServices;

        model.put("categories",listOfServiceCategories);
        model.put("transportSubcategories",finalListOfTransportServices);
    }
}
