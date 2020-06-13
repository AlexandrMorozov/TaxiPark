package com.taxipark.admin;

import com.taxipark.services.NavBarLoader;
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

    @Autowired
    private NavBarLoader navBarLoader;/*=new NavBarLoader();*/

    @PostMapping("/adminportal/signin")
    public String signInAdmin(@RequestParam String login,
                              @RequestParam String password,
                              HttpServletRequest request,
                              Map<String,Object> model)
    {
        if(personnelRepo.findByLoginAndPassword(login,password)==null)
        {
            return "admin/main/AdminAuthorization";
        }

        request.getSession().setAttribute("adminlogin", login);
        model.put("employeeName",login);

        navBarLoader.checkRoleAdmin(model,personnelRepo,login);

        return "redirect:/adminportal";
    }

    @GetMapping("/adminportal/signout")
    public String signOutAdmin(HttpServletRequest request,
                               Map<String,Object> model)
    {
        request.getSession().invalidate();

        return "redirect:/adminportal";
    }

    @GetMapping("/adminportal")
    public String adminMainMenu(HttpSession session,
                                Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            System.out.println(session.getId());
            return "admin/main/AdminAuthorization";
        }

        navBarLoader.checkRoleAdmin(model,personnelRepo,login);


        return "admin/main/MainMenuAdmin";
    }

}
