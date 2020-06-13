package com.taxipark.admin;

import com.taxipark.dbmodel.ClientOrder;
import com.taxipark.dbmodel.Payments_List;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dbmodel.Transport;
import com.taxipark.repos.ClientOrderRepo;
import com.taxipark.repos.Payments_ListRepo;
import com.taxipark.repos.PersonnelRepo;
import com.taxipark.repos.TransportRepo;
import com.taxipark.services.NavBarLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PersonnelAccountController
{
    @Autowired
    PersonnelRepo personnelRepo;
    @Autowired
    ClientOrderRepo clientOrderRepo;
    @Autowired
    Payments_ListRepo paymentsListRepo;
    @Autowired
    TransportRepo transportRepo;
    @Autowired
    private NavBarLoader navBarLoader/*=new NavBarLoader()*/;

    @GetMapping("/adminportal/employeepage")
    public String accountMainMenu(@RequestParam(name = "log") String user, HttpSession session, Map<String,Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        if(navBarLoader.checkRoleAdmin(model,personnelRepo,login))
        {
            return "redirect:/adminportal";
        }

        if(!login.equals(user))
        {
            return "redirect:/adminportal";
        }

        Personnel personnel=personnelRepo.findByLogin(login);
        Transport transport=transportRepo.findByTransportID(personnel.getTransportID());

        if(personnel.getPersonnelProfession().getPositionName().equals("Водитель"))
        {
            if(transport==null)
            {
                return "redirect:/adminportal";
            }
            else if(transport.getType().equals("Пассажирский"))
            {
                model.put("isAllowed",true);
                model.put("isAllowed1",false);
                model.put("isAllowed2",false);
                model.put("isTaxi",true);
            }
            else if(transport.getType().equals("Грузовой"))
            {
                model.put("isAllowed",true);
                model.put("isAllowed1",true);
                model.put("isAllowed2",false);
                model.put("isTaxi",false);
            }
        }
        else
        {
            model.put("isAllowed",false);
            model.put("isAllowed1",false);
            model.put("isAllowed2",true);
            model.put("isTaxi",false);
        }

        ArrayList<ClientOrder> relatedOrders=new ArrayList<>();

        System.out.println(personnel.getRelatedOrders().size());
        for(int i=0;i<personnel.getRelatedOrders().size();i++)
        {
            if(personnel.getRelatedOrders().get(i).getStatus().equals("active"))
            {
                relatedOrders.add(personnel.getRelatedOrders().get(i));
            }
        }

        model.put("allOrders", relatedOrders);


      return "admin/adminuser/EmployeeAccount";
    }
    @GetMapping("/adminportal/completeorder")
    public String completeClientOrder(@RequestParam(name="orderID") int orderID, HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        ClientOrder completedOrder=clientOrderRepo.findByOrderID(orderID);
        completedOrder.setStatus("completed");
        clientOrderRepo.save(completedOrder);

        String[] parsedDate=completedOrder.getDateOfOrder().split("-");

        if(parsedDate[1].charAt(0)=='0')
        {
            parsedDate[1]=String.valueOf(parsedDate[1].charAt(1));
        }


        Payments_List companyIncome=createPayment(completedOrder.getDateOfOrder(),Integer.parseInt(parsedDate[0]), parsedDate[1],
                completedOrder.getCost(),completedOrder.getAssignedEmployee().getPersonnelID(),
                completedOrder.getOrderedService().getServicesID(),
                completedOrder.getOrderedService().getCategoryID(),"income");

        paymentsListRepo.save(companyIncome);

        if( completedOrder.getAssignedEmployee().getPersonnelProfession().getPositionName().equals("Водитель"))
        {
            double personnelCoefficient=Double.parseDouble(completedOrder.getAssignedEmployee().getPersonnelProfession().getPositionSalary());
            double personnelPayment=Math.round(completedOrder.getCost()*(personnelCoefficient/100.0));

            Payments_List personnelIncome=createPayment(completedOrder.getDateOfOrder(),Integer.parseInt(parsedDate[0]), parsedDate[1],
                    personnelPayment,completedOrder.getAssignedEmployee().getPersonnelID(),
                    completedOrder.getOrderedService().getServicesID(),
                    completedOrder.getOrderedService().getCategoryID(),"expence");

            paymentsListRepo.save(personnelIncome);

        }

        return "redirect:/adminportal/employeepage?log="+login;

    }

    private Payments_List createPayment(String paymentDate, int paymentYear, String paymentMonth, double paymentSum,
                                        Integer personnelID, Integer serviceID, Integer serviceCategoryID, String paymentType)
    {
        Payments_List newPayment=new Payments_List(paymentDate,paymentYear, paymentMonth, paymentSum,personnelID,
                serviceID, serviceCategoryID,paymentType);

        return newPayment;
    }
}
