package com.taxipark.admin;

import com.taxipark.services.NavBarLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

import java.util.Map;

@Controller
public class StatisticsController
{
    private NavBarLoader navBarLoader=new NavBarLoader();


    @GetMapping("/adminportal/statistics")
    public String statisticsMenu(HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        return "admin/statistics/StatisticsMenu";
    }
}
