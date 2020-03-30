package core;

import dbmodel.ClientOrder;
import dbmodel.Clients;
import dto.FullOrderInfoDto;
import logic.NavBarLoader;
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
    public String registrationPage(Map<String,Object> model)
    {
        return "Registration";
    }

    @GetMapping("/Authorization")
    public String authorizationPage()
    {
        return "Authorization";
    }

    @PostMapping("/Registration.mustache/registrate")
    public String addNewClient(@RequestParam String name, @RequestParam String surname, @RequestParam String login, @RequestParam String password,
                               @RequestParam String dob, @RequestParam String telnum, HttpServletRequest request, Map<String,Object> model)
    {

        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        if(clientsRepo.findByClientLogin(login)==null)
        {
            Clients newClient=new Clients(name,surname,login,password,dob,telnum);
            System.out.println(newClient);
            clientsRepo.save(newClient);

            request.getSession().setAttribute("login", login);

            model.put("authEntLink","/UserAccount?log="+login);
            model.put("authEntLinkName",login);
        }
        else
        {
            //model.
            return "Registration";
        }



      return "MainMenu";
    }

    @GetMapping("/UserAccount")
    public String openClientAccount(@RequestParam(name = "log") String user, HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        if(!login.equals(user))
        {
            return "MainMenu";
        }

        Clients currentClient=clientsRepo.findByClientLogin(login);
        model.put("client",currentClient);
        loadUserAccountOrders(currentClient,model);

        return "UserAccount";


    }

    @GetMapping("/SignOut")
    public String signOutClient(HttpServletRequest request, Map<String,Object> model)
    {
        request.getSession().invalidate();

        navBarLoader.checkAuthorization(null,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        return "MainMenu";
    }

    @PostMapping("/SignIn")
    public String signInClient(@RequestParam String login, @RequestParam String password, HttpServletRequest request, Map<String,Object> model)
    {

        if(clientsRepo.findByClientLoginAndClientPassword(login,password)==null)
        {
            return "Authorization";
        }

        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);
        request.getSession().setAttribute("login", login);
        model.put("authEntLink","/UserAccount?log="+login);
        model.put("authEntLinkName",login);

        return "MainMenu";
    }

    @GetMapping("/DeleteOrder")
    public String deleteOrder(@RequestParam(name="orderID") int orderID, HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        Clients currentClient=clientsRepo.findByClientLogin(login);
        ClientOrder deletedOrder=clientOrderRepo.findByOrderID(orderID);

        if(currentClient.getClientID()!=deletedOrder.getClientID())
        {
            return "MainMenu";
        }

        Integer routeID=deletedOrder.getRouteID();

        clientOrderRepo.deleteById(orderID);

        if(deletedOrder.getRouteID()!=null)
        {
            order_routeRepo.deleteById(routeID);
        }

        model.put("client",currentClient);
        loadUserAccountOrders(currentClient,model);

        return "UserAccount";
    }

    private void loadUserAccountOrders(Clients currentClient, Map<String,Object> model)
    {
        Iterable<FullOrderInfoDto> allCurrentClientOrders=clientOrderRepo.findFullOrderInfo(currentClient.getClientID(),"active");
        model.put("orders",allCurrentClientOrders);

    }
}
