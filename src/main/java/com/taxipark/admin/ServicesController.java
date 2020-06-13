package com.taxipark.admin;

import com.taxipark.dbmodel.Customer_Services_Data;
import com.taxipark.dbmodel.Services;
import com.taxipark.dbmodel.Services_Category;
import com.taxipark.repos.PersonnelRepo;
import com.taxipark.services.NavBarLoader;
import com.taxipark.repos.Customer_Services_DataRepo;
import com.taxipark.repos.ServicesRepo;
import com.taxipark.repos.Services_CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class ServicesController
{
    @Value("${upload.path}")
    String uploadPath;

    @Autowired
    Customer_Services_DataRepo customerServicesDataRepo;
    @Autowired
    ServicesRepo servicesRepo;
    @Autowired
    Services_CategoryRepo servicesCategoryRepo;
    @Autowired
    PersonnelRepo personnelRepo;

    @Autowired
    private NavBarLoader navBarLoader/*=new NavBarLoader()*/;


    @GetMapping("/adminportal/categories")
    public String mainCategoriesMenu(HttpSession session, Map<String, Object> model)
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

        Iterable<Services_Category> allCategories=servicesCategoryRepo.findAllCategories();
        model.put("categoriesList",allCategories);

        return "admin/services/CategoriesMain";
    }

    @GetMapping("/adminportal/categories/category")
    public String categoryPage(@RequestParam(name="serviceCategoryID") int serviceCategoryID, HttpSession session, Map<String, Object> model)
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

        Services_Category currentCategory=servicesCategoryRepo.findByServiceCategoryID(serviceCategoryID);
        Iterable<Services> currentCategoryServices=servicesRepo.findAllServicesByCategoryID(serviceCategoryID);

        model.put("category",currentCategory);
        model.put("servicesList",currentCategoryServices);

        return "admin/services/CategoriesPage";
    }

    @GetMapping("/adminportal/categories/category/service")
    public String servicePage(@RequestParam(name="servicesID") int servicesID, HttpSession session, Map<String, Object> model)
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

        boolean isServicesDataPresent=false;
        double price;
        String currency;
        String priceTitle;

        Services currentService=servicesRepo.findByServicesID(servicesID);
        Customer_Services_Data currentServicesData=customerServicesDataRepo.findByMainServiceData(currentService)/*findByServiceID(servicesID)*/;


        if(currentServicesData!=null)
        {
            isServicesDataPresent=true;
            model.put("servicesData",currentServicesData);
        }

        if(currentService.getCalculatablePrice()==null)
        {
            priceTitle="Стоимость от";
            currency="руб.";
            price=currentService.getPrice();
        }
        else
        {
            priceTitle="Стоимость";
            currency="руб./км.";
            price=currentService.getCalculatablePrice();
        }


        model.put("priceTitle",priceTitle);
        model.put("currency",currency);
        model.put("servicePrice",price);
        model.put("service",currentService);
        model.put("isServicesDataPresent",isServicesDataPresent);


        return "admin/services/ServicesPage";
    }



    ////////////////////// Services Management /////////////////////////
    @GetMapping("/adminportal/categories/category/createcategory")
    public String categoryCreationMenu(HttpSession session, Map<String, Object> model)
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

        return "admin/services/CategoriesCreation";
    }

    @PostMapping("/adminportal/categories/category/create")
    public String createCategory(@RequestParam(name = "sn") String serviceCategoryName,@RequestParam(name = "desc") String serviceCategoryDescription,
                                 @RequestParam(name = "fto") /*String*/MultipartFile serviceCategoryFoto,/*String serviceCategoryType,*/ HttpSession session, Map<String, Object> model)
    {

        String resultFilename= loadFoto(serviceCategoryFoto);

        Services_Category newCategory=new Services_Category(serviceCategoryName,serviceCategoryDescription,/*serviceCategoryFoto*/resultFilename, /*serviceCategoryType*/"customer service");

        servicesCategoryRepo.save(newCategory);

        return "redirect:/adminportal/categories/category?serviceCategoryID="+newCategory.getServiceCategoryID();
    }

    @GetMapping("/adminportal/categories/category/service/createservice")
    public String serviceCreationMenu(@RequestParam(name = "serviceCategoryID") int serviceCategoryID, Map<String,Object> model,HttpSession session)
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

        model.put("category",serviceCategoryID);

        return "admin/services/ServicesCreationPage";
    }

    @PostMapping("/adminportal/categories/category/service/create")
    public String createService(@RequestParam(name = "sn") String serviceName,@RequestParam(name = "desc") String serviceDescription,
                                @RequestParam(name = "fto") /*String*/MultipartFile foto,@RequestParam(name = "prs") double price,/* Double calculatablePrice,*/
                                @RequestParam(name = "categ") int categoryID,
                                @RequestParam(name = "comt",required = false) Double completionTime,
                                @RequestParam(name = "gur",required = false) Integer guaranteeTime,
                                HttpSession session, Map<String, Object> model)
    {

        Services newService;

        String resultFilename= loadFoto(foto);


        if(completionTime!=null)
        {
            newService=new Services(serviceName,serviceDescription,/*foto*/resultFilename,price,null,categoryID);

            servicesRepo.save(newService);

            Customer_Services_Data newCustomerServicesData=new Customer_Services_Data(completionTime,guaranteeTime,newService/*.getServicesID()*/);

            customerServicesDataRepo.save(newCustomerServicesData);
        }

        else
        {
            newService=new Services(serviceName,serviceDescription,/*foto*/resultFilename,0,price,categoryID);

            servicesRepo.save(newService);
        }


        return "redirect:/adminportal/categories/category/service?servicesID="+newService.getServicesID();
    }


    @GetMapping("/adminportal/categories/category/deletecategory")
    public String deleteCategory(@RequestParam(name="serviceCategoryID") int categoryID, HttpSession session, Map<String, Object> model)
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

        String oldFotoName=servicesCategoryRepo.findByServiceCategoryID(categoryID).getServiceCategoryFoto();

        ///////////////////////
        servicesCategoryRepo.deleteById(categoryID);

        deleteFoto(oldFotoName);

        return "redirect:/adminportal/categories";
    }

    @GetMapping("/adminportal/categories/category/service/deleteservice")
    public String deleteService(@RequestParam(name="servicesID") int serviceID, HttpSession session, Map<String, Object> model)
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

        Services currentService=servicesRepo.findByServicesID(serviceID);
        String oldFotoName=currentService.getFoto();
        int categoryID=currentService.getCategoryID();
        ///////////////////////////

        servicesRepo.delete(currentService);

        deleteFoto(oldFotoName);

        return "redirect:/adminportal/categories/category?serviceCategoryID="+categoryID;

    }

    @GetMapping("/adminportal/categories/category/updatecategory")
    public String updateCategoryMenu(@RequestParam(name = "serviceCategoryID") int categoryID, Map<String,Object> model,HttpSession session)
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

        model.put("category",servicesCategoryRepo.findByServiceCategoryID(categoryID));

        return "admin/services/CategoriesModificationPage";
    }

    @PostMapping("/adminportal/categories/category/update")
    public String updateCategory(@RequestParam(name = "sn") String serviceCategoryName, @RequestParam(name = "desc") String serviceCategoryDescription,
                                 @RequestParam(name = "fto") MultipartFile /*String*/ serviceCategoryFoto, @RequestParam(name = "serviceCategoryID") int serviceCategoryID,
                                 HttpSession session, Map<String, Object> model)
    {

        Services_Category currentCategory=servicesCategoryRepo.findByServiceCategoryID(serviceCategoryID);

        String fileName;

        if(!serviceCategoryFoto.isEmpty())
        {
            fileName=loadFoto(serviceCategoryFoto);

            deleteFoto(currentCategory.getServiceCategoryFoto());

            currentCategory.setServiceCategoryFoto(fileName/*foto*/);
        }

        currentCategory.setServiceCategoryName(serviceCategoryName);
        currentCategory.setServiceCategoryDescription(serviceCategoryDescription);

        servicesCategoryRepo.save(currentCategory);


        return "redirect:/adminportal/categories/category?serviceCategoryID="+serviceCategoryID;
    }

    @GetMapping("/adminportal/categories/category/service/updateservice")
    public String updateServiceMenu(@RequestParam(name = "servicesID") int serviceID, Map<String,Object> model,HttpSession session)
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

        boolean isCustomerService=false;
        Double price;

        Services selectedService=servicesRepo.findByServicesID(serviceID);
        Customer_Services_Data additionalServicesData=customerServicesDataRepo.findByMainServiceData(selectedService)/*findByServiceID(serviceID)*/;

        System.out.println(selectedService.getFoto());//

        if(additionalServicesData!=null)
        {
            isCustomerService=true;
            price=selectedService.getPrice();

            model.put("customerServData",additionalServicesData);

        }
        else
        {
            price=selectedService.getCalculatablePrice();
        }

        model.put("service",selectedService);
        model.put("isCustomerService",isCustomerService);
        model.put("price",price);

        return "admin/services/ServicesModificationPage";
    }

    @PostMapping("/adminportal/categories/category/service/update")
    public String updateService(@RequestParam(name = "sn") String serviceName,@RequestParam(name = "desc") String serviceDescription,
                                @RequestParam(name = "fto") /*String*/MultipartFile foto, @RequestParam(name = "prs") double price, /*Double calculatablePrice,*/
                                @RequestParam(name = "servicesID") int servicesID,
                                @RequestParam(name = "comt",required = false) Double completionTime,
                                @RequestParam(name = "gur",required = false) Integer guaranteeTime,
                                HttpSession session, Map<String, Object> model)
    {

        Services currentService=servicesRepo.findByServicesID(servicesID);
        Customer_Services_Data currentCustomerServicesData=customerServicesDataRepo.findByMainServiceData(currentService)/*findByServiceID(servicesID)*/;

        String fileName;

        if(!foto.isEmpty())
        {
            fileName=loadFoto(foto);

            deleteFoto(currentService.getFoto());

            currentService.setFoto(fileName/*foto*/);
        }


        currentService.setServiceName(serviceName);
        currentService.setServiceDescription(serviceDescription);

        if(currentCustomerServicesData!=null)
        {
            currentService.setPrice(price);
        }
        else
        {
            currentService.setCalculatablePrice(price);
        }


        servicesRepo.save(currentService);


        if(currentCustomerServicesData!=null)
        {
            currentCustomerServicesData.setCompletionTime(completionTime);
            currentCustomerServicesData.setGuaranteeTime(guaranteeTime);

            customerServicesDataRepo.save(currentCustomerServicesData);
        }

        return "redirect:/adminportal/categories/category/service?servicesID="+servicesID;
    }


    ////////////////////////////////


    private String loadFoto(MultipartFile serviceCategoryFoto)
    {

        String uuidFile=UUID.randomUUID().toString();
        String resultFilename=uuidFile+"."+serviceCategoryFoto.getOriginalFilename();

        try
        {
            serviceCategoryFoto.transferTo(new File(uploadPath+"/"+resultFilename));
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }

        return resultFilename;
    }

    private void deleteFoto(String fileName)
    {
        File oldFoto=new File(uploadPath+"/"+fileName);

        oldFoto.delete();

    }
}
