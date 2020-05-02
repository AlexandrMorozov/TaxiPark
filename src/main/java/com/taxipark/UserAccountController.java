package com.taxipark;

import com.taxipark.repos.*;
import com.taxipark.dbmodel.ClientOrder;
import com.taxipark.dbmodel.Clients;
import com.taxipark.dto.FullOrderInfoDto;
import com.taxipark.logic.NavBarLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserAccountController
{
    @Autowired
    private ClientsRepo clientsRepo;
    @Autowired
    private Services_CategoryRepo services_categoryRepo;
    @Autowired
    private ServicesRepo servicesRepo;
    @Autowired
    private ClientOrderRepo clientOrderRepo;
    @Autowired
    private Order_RouteRepo order_routeRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    @GetMapping("/Registration.mustache")
    public String registrationPage()
    {
        return "user/Registration";
    }

    @GetMapping("/Authorization")
    public String authorizationPage()
    {
        return "user/Authorization";
    }

    @PostMapping("/Registration.mustache/registrate")
    public String addNewClient(@RequestParam String name, @RequestParam String surname, @RequestParam String login, @RequestParam String password,
                               @RequestParam String dob, @RequestParam String telnum, HttpServletRequest request)
    {

        if(clientsRepo.findByClientLogin(login)==null)
        {
            Clients newClient=new Clients(name,surname,login,password,dob,telnum);
            clientsRepo.save(newClient);

            request.getSession().setAttribute("login", login);

        }
        else
        {
            return "redirect:/Registration.mustache";
        }



      return "redirect:/UserAccount?log="+login;
    }

    @GetMapping("/UserAccount")
    public String openClientAccount(@RequestParam(name = "log") String user, HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        if(!login.equals(user))
        {
            return "user/MainMenu";
        }

        Clients currentClient=clientsRepo.findByClientLogin(login);
        model.put("client",currentClient);

        Iterable<FullOrderInfoDto> allCurrentClientOrders=clientOrderRepo.findFullOrderInfo(currentClient.getClientID(),"active");
        model.put("orders",allCurrentClientOrders);

        return "user/UserAccount";

    }

    @GetMapping("/SignOut")
    public String signOutClient(HttpServletRequest request)
    {
        request.getSession().invalidate();

        return "redirect:/";
    }

    @PostMapping("/SignIn")
    public String signInClient(@RequestParam String login, @RequestParam String password, HttpServletRequest request)
    {

        if(clientsRepo.findByClientLoginAndClientPassword(login,password)==null)
        {
            return "redirect:/Authorization";
        }

        request.getSession().setAttribute("login", login);

        return "redirect:/UserAccount?log="+login;
    }

    @GetMapping("/DeleteOrder")
    public String deleteOrder(@RequestParam(name="orderID") int orderID, HttpSession session)
    {
        String login=(String) session.getAttribute("login");

        Clients currentClient=clientsRepo.findByClientLogin(login);
        ClientOrder deletedOrder=clientOrderRepo.findByOrderID(orderID);

        if(currentClient.getClientID()!=deletedOrder.getClientID())
        {
            return "user/MainMenu";
        }

        clientOrderRepo.deleteById(orderID);

        return "redirect:/UserAccount?log="+login;
    }

}
