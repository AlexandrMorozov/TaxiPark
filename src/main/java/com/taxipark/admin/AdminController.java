package com.taxipark.admin;

import com.taxipark.logic.NavBarLoader;
import com.taxipark.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    PersonnelRepo personnelRepo;
    @Autowired
    Driver_DataRepo driverDataRepo;
    @Autowired
    Customer_Services_DataRepo customerServicesDataRepo;
    @Autowired
    ServicesRepo servicesRepo;
    @Autowired
    Services_CategoryRepo servicesCategoryRepo;
    @Autowired
    TransportRepo transportRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

   /* @GetMapping("/adminportal")
    public String adminAuthorizationMenu(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/MainMenuAdmin";
        }

        return "admin/main/AdminAuthorization";
    }*/

    @PostMapping("/adminportal/signin")
    public String signInAdmin(@RequestParam String login, @RequestParam String password, HttpServletRequest request, Map<String,Object> model)
    {
        if(personnelRepo.findByLoginAndPassword(login,password)==null)
        {
            return "admin/main/AdminAuthorization";
        }

        request.getSession().setAttribute("adminlogin", login);
        model.put("employeeName",login);

        return "admin/main/MainMenuAdmin";
    }

    @GetMapping("/adminportal/signout")
    public String signOutAdmin(HttpServletRequest request, Map<String,Object> model)
    {
        request.getSession().invalidate();

        return "admin/main/AdminAuthorization";
    }

    @GetMapping("/adminportal")
    public String adminMainMenu(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        return "admin/main/MainMenuAdmin";
    }

}
