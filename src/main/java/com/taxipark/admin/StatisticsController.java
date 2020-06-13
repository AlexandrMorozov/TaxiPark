package com.taxipark.admin;

import com.taxipark.repos.PersonnelRepo;
import com.taxipark.services.NavBarLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

import java.util.Map;

@Controller
public class StatisticsController
{
    @Autowired
    private NavBarLoader navBarLoader/*=new NavBarLoader()*/;
    @Autowired
    private PersonnelRepo personnelRepo;


    @GetMapping("/adminportal/statistics")
    public String statisticsMenu(HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        if(!navBarLoader.checkRoleAdmin(model,personnelRepo,login))
        {
            return "redirect:/adminportal";
        }

        return "admin/statistics/StatisticsMenu";
    }

}
