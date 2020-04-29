package com.taxipark.admin;

import com.taxipark.dbmodel.Customer_Services_Data;
import com.taxipark.dbmodel.Services;
import com.taxipark.dbmodel.Services_Category;
import com.taxipark.repos.Customer_Services_DataRepo;
import com.taxipark.repos.ServicesRepo;
import com.taxipark.repos.Services_CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ServicesController
{

    @Autowired
    Customer_Services_DataRepo customerServicesDataRepo;
    @Autowired
    ServicesRepo servicesRepo;
    @Autowired
    Services_CategoryRepo servicesCategoryRepo;


    @GetMapping("/adminportal/categories")
    public String mainCategoriesMenu(HttpSession session, Map<String, Object> model)
    {
        Iterable<Services_Category> allCategories=servicesCategoryRepo.findAllCategories();
        model.put("categoriesList",allCategories);

        return "admin/services/CategoriesMain";
    }

    @GetMapping("/adminportal/categories/category")
    public String categoryPage(@RequestParam(name="serviceCategoryID") int serviceCategoryID, HttpSession session, Map<String, Object> model)
    {

        Services_Category currentCategory=servicesCategoryRepo.findByServiceCategoryID(serviceCategoryID);
        Iterable<Services> currentCategoryServices=servicesRepo.findAllServicesByCategoryID(serviceCategoryID);

        model.put("category",currentCategory);
        model.put("servicesList",currentCategoryServices);

        return "admin/services/CategoriesPage";
    }

    @GetMapping("/adminportal/categories/category/service")
    public String servicePage(@RequestParam(name="servicesID") int servicesID, HttpSession session, Map<String, Object> model)
    {
        boolean isServicesDataPresent=false;
        boolean isCustomerService=false;
        double price;
        String currency;
        String priceTitle;

        Services currentService=servicesRepo.findByServicesID(servicesID);
        Customer_Services_Data currentServicesData;


     /*   if(currentService.getCustomerServicesDataID()!=null)
        {
            //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
            currentServicesData=customerServicesDataRepo.findByCustomerServicesDataID(currentService.getCustomerServicesDataID());

            isServicesDataPresent=true;
            model.put("servicesData",currentServicesData);

        }*/

        System.out.println(currentService.getCalculatablePrice());
        if(currentService.getCalculatablePrice()==null/*.equals(null)*/)
        {
            isCustomerService=true;
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
        model.put("isCustomerService",isCustomerService);
        model.put("service",currentService);
        model.put("isServicesDataPresent",isServicesDataPresent);


        return "admin/services/ServicesPage";
    }



    ////////////////////// Services Management /////////////////////////
    @GetMapping("/adminportal/1")
    public String createCategory(String serviceCategoryName, String serviceCategoryDescription, String serviceCategoryFoto,
                                 String serviceCategoryType, HttpSession session, Map<String, Object> model)
    {
        Services_Category newCategory=new Services_Category(serviceCategoryName,serviceCategoryDescription,serviceCategoryFoto,serviceCategoryType);
        servicesCategoryRepo.save(newCategory);
        return "";
    }

    @GetMapping("/adminportal/2")
    public String createService(String serviceName, String serviceDescription, String foto, double price, Double calculatablePrice,
                                int categoryID, Integer customerServicesDataID, HttpSession session, Map<String, Object> model)
    {

       // Services newService=new Services(serviceName,serviceDescription,foto,price,calculatablePrice,categoryID,customerServicesDataID);
        //servicesRepo.save(newService);
        return "";
    }



    @GetMapping("/adminportal/3")
    public String deleteCategory(@RequestParam(name="id") int categoryID, HttpSession session, Map<String, Object> model)
    {

        ///////////////////////
        if(servicesCategoryRepo.findByServiceCategoryID(categoryID).getServiceCategoryType().equals("customer service"))
        {
            List<Services> categoryServices=servicesRepo.findAllByCategoryID(categoryID);

            for(int i=0;i<categoryServices.size();i++)
            {
                /*Customer_Services_Data customerData=customerServicesDataRepo.
                        findByCustomerServicesDataID(categoryServices.get(i).getCustomerServicesDataID());*/

                //customerServicesDataRepo.deleteById(categoryServices.get(i).getCustomerServicesDataID());
            }
        }

        servicesCategoryRepo.deleteById(categoryID);


        return "";
    }

    @GetMapping("/adminportal/4")
    public String deleteService(@RequestParam(name="id") int serviceID, HttpSession session, Map<String, Object> model)
    {
        Services currentService=servicesRepo.findByServicesID(serviceID);

        ///////////////////////////
       /* if(currentService.getCustomerServicesDataID()!=null)
        {
            customerServicesDataRepo.deleteById(currentService.getCustomerServicesDataID());
        }*/

        servicesRepo.delete(currentService);

        return "";

    }

    @GetMapping("/adminportal/5")
    public String updateCategory(String serviceCategoryName, String serviceCategoryDescription,
                                 String serviceCategoryFoto, int serviceCategoryID,HttpSession session, Map<String, Object> model)
    {
        servicesCategoryRepo.updateServicesCategory(serviceCategoryName,serviceCategoryDescription, serviceCategoryFoto,serviceCategoryID);

        return "";
    }

    @GetMapping("/adminportal/6")
    public String updateService(String serviceName,String serviceDescription,String foto,
                                double price, Double calculatablePrice,int servicesID,  double completionTime,
                                int guaranteeTime, HttpSession session, Map<String, Object> model)
    {
        servicesRepo.updateService(serviceName,serviceDescription,foto,price,calculatablePrice,servicesID);
       // customerServicesDataRepo.updateCustomerServicesData(completionTime,guaranteeTime,servicesID);
        return "";
    }
}
