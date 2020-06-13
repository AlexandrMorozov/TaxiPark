package com.taxipark.admin;

import com.taxipark.dbmodel.Transport;
import com.taxipark.repos.PersonnelRepo;
import com.taxipark.services.NavBarLoader;
import com.taxipark.repos.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TransportController
{
    @Autowired
    TransportRepo transportRepo;
    @Autowired
    PersonnelRepo personnelRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    @GetMapping("/adminportal/transport")
    public String mainTransportMenu(HttpSession session, Map<String, Object> model)
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

        Iterable<Transport> allTransport=transportRepo.findAllTransport();
        model.put("transportList",allTransport);

        return "admin/transport/TransportMain";
    }

    @GetMapping("/adminportal/transport/currenttransport")
    public String transportPage(@RequestParam(name="transportID") int transportID, HttpSession session, Map<String, Object> model)
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

        Transport transport=transportRepo.findByTransportID(transportID);
        model.put("transport",transport);

        return "admin/transport/TransportPage";
    }



    @GetMapping("/adminportal/transport/createtransport")
    public String transportCreationMenu(HttpSession session, Map<String, Object> model)
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

        return "admin/transport/TransportCreation";
    }

    @PostMapping("/adminportal/transport/addtransport")
    public String createTransport(@RequestParam(name = "cmark") String carBrand, @RequestParam(name = "cmodel") String carModel,
                                  @RequestParam(name = "ccolor") String carColor, @RequestParam(name = "crnum") String licensePlate,
                                  @RequestParam(name = "cvin") String vin, @RequestParam(name = "cyom") String yearOfManufacture,
                                  @RequestParam(name = "ccn") String carcassNum, @RequestParam(name = "cen")  String engineNum,
                                  @RequestParam(name="ctp") String carType,
                                  HttpSession session, Map<String, Object> model)
    {
        Transport newTransport=new Transport(carBrand,carModel,carColor,
                licensePlate,vin,carcassNum, engineNum,yearOfManufacture,carType);
        transportRepo.save(newTransport);

        return "redirect:/adminportal/transport/currenttransport?transportID="+newTransport.getTransportID();
    }

    @GetMapping("/adminportal/transport/modifytransport")
    public String transportModificationMenu(@RequestParam(name="transportID") int transportID, HttpSession session, Map<String, Object> model)
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

        Transport transport=transportRepo.findByTransportID(transportID);
        model.put("transport",transport);

        return "admin/transport/TransportModificationPage";
    }

    @PostMapping("/adminportal/transport/modify")
    public String modifyTransport(@RequestParam(name="cmodel") String carModel,@RequestParam(name="ccolor") String carColor,
                                  @RequestParam(name="crnum") String licensePlate, @RequestParam(name="cvin") String vin,
                                  @RequestParam(name="ccn") String carcassNum, @RequestParam(name="cen") String engineNum,
                                  @RequestParam(name="transportID") int transportID,
                                  HttpSession session, Map<String, Object> model)
    {

        Transport transport=transportRepo.findByTransportID(transportID);

        transport.setCarModel(carModel);
        transport.setCarColor(carColor);
        transport.setLicensePlate(licensePlate);
        transport.setVin(vin);
        transport.setCarcassNum(carcassNum);
        transport.setEngineNum(engineNum);
        transport./*setOtherAttributes*/setType(null);

        transportRepo.save(transport);
        
        return "redirect:/adminportal/transport/currenttransport?transportID="+transportID;
    }

    @GetMapping("/adminportal/transport/deletetransport")
    public String deleteTransport(@RequestParam(name="transportID") int transportID,HttpSession session, Map<String, Object> model)
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

        transportRepo.deleteById(transportID);

        return "redirect:/adminportal/transport";
    }
}
