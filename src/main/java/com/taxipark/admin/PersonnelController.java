package com.taxipark.admin;

import com.taxipark.dbmodel.Driver_Data;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dbmodel.Transport;
import com.taxipark.logic.NavBarLoader;
import com.taxipark.repos.Driver_DataRepo;
import com.taxipark.repos.PersonnelRepo;
import com.taxipark.repos.TransportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class PersonnelController
{
    @Autowired
    PersonnelRepo personnelRepo;
    @Autowired
    Driver_DataRepo driverDataRepo;
    @Autowired
    TransportRepo transportRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    @GetMapping("/adminportal/personnel")
    public String mainPersonnelMenu(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        List<Personnel> allPersonnel=personnelRepo.findAllPersonnel();
        model.put("personnelList",allPersonnel);

        return "admin/PersonnelMain";
    }

    @GetMapping("/adminportal/personnel/worker")
    public String employeePage(@RequestParam(name="personnelID") int personnelID,
            /*@RequestParam(name="position") int position,*/ HttpSession session, Map<String, Object> model)

    {

        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        boolean isHaveDriverLicense=false;
        Personnel worker=personnelRepo.findByPersonnelID(personnelID);
        /////////////////////////////////////
        if(worker.getEmployeePosition().equals("Водитель"))
        {
            isHaveDriverLicense=true;
            Driver_Data driverData=driverDataRepo.findByOwnerId(worker.getPersonnelID());
            model.put("driverData",driverData);

            //////////////////////////////
            if(worker.getTransportID()==null)
            {
                model.put("carData","-");
            }
            else
            {
                Transport assignedTransport=transportRepo.findByTransportID(worker.getTransportID());

                String assignedTransportData=assignedTransport.getCarBrand()+
                        " "+assignedTransport.getCarModel()+" "+assignedTransport.getVin();

                model.put("carData",assignedTransportData);
            }

        }

        String[] dividedName=worker.getFullName().split(" ");

        model.put("lname",dividedName[0]);
        model.put("name",dividedName[1]);
        model.put("mname",dividedName[2]);

        model.put("isHaveDriverLicense",isHaveDriverLicense);
        model.put("personnel",worker);



        return "admin/WorkerPage";
    }

    @GetMapping("/adminportal/personnel/deleteemployee")
    public String fireEmployee(@RequestParam(name="personnelID") int personnelID, HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        personnelRepo.deleteById(personnelID);

        return mainPersonnelMenu(session,model);
    }

    @GetMapping("/adminportal/personnel/modifyemployee")
    public String employeeModificationMenu(@RequestParam(name="personnelID") int personnelID, HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        String[] educationLevels={"Начальное","Среднее","Среднее общее","Высшее"};
        String[] licenseLevels={"A","B","C"};

        boolean isHaveDriverLicense=false;
        Personnel worker=personnelRepo.findByPersonnelID(personnelID);

        if(worker.getEmployeePosition().equals("Водитель"))
        {
            isHaveDriverLicense=true;
            Driver_Data driverData=driverDataRepo.findByOwnerId(worker.getPersonnelID());
            model.put("driverData",driverData);

            //////////////////////////
            Iterable<Transport> unassignedTransports=transportRepo.findAllUnassignedCars();
            model.put("carData",unassignedTransports);

            /////////////////////////
           // Transport currentTransport=transportRepo.findByTransportID(worker.getTransportID());
            //model.put("carDataMain",currentTransport);

            String[] dividedLicense=driverData.getLicenseCategory().split(" ");

            ////////////////////////////
            for(int i=0;i<licenseLevels.length;i++)
            {
                for(int j=0;j<dividedLicense.length;j++)
                {
                    if(dividedLicense[j].equals(licenseLevels[i]))
                    {
                        model.put("ch"+i,"checked");
                    }
                    else
                    {
                        model.put("ch"+i,"");
                    }
                }
            }
            ////////////////////////////////
        }

        String[] dividedName=worker.getFullName().split(" ");

        model.put("lname",dividedName[0]);
        model.put("name",dividedName[1]);
        model.put("mname",dividedName[2]);


        for(int i=0;i<educationLevels.length;i++)
        {
            if(worker.getEducationDegree().equals(educationLevels[i]))
            {
                model.put("ed"+i,"selected");
            }
            else
            {
                model.put("ed"+i,"");
            }
        }


        model.put("personnel",worker);
        model.put("isHaveDriverLicense",isHaveDriverLicense);

        return "admin/WorkerModificationPage";
    }

    @PostMapping("/adminportal/personnel/modify")
    public String modifyEmployee(@RequestParam(name="personnelID") int personnelID,@RequestParam(name="name") String fName,@RequestParam(name="mname") String mName,@RequestParam(name="lname") String lName,
                                 @RequestParam(name="aor") String address, @RequestParam(name="pn")String passportID, @RequestParam(name="loe") String educationDegree,@RequestParam(name="not") String phoneNumber,@RequestParam(name="asc",required = false) Integer transportID,
                                 /*@RequestParam(name = "pr") String employeePosition*//*@RequestParam(name = "lg") String login*/@RequestParam(name = "psw") String password,
                                 @RequestParam(name="ln",required = false) String licenseNum,@RequestParam(name="ib",required = false) String licenseIssuedBy,@RequestParam(name="a",required = false) String[] licenseCategory, @RequestParam(name="lvu",required = false) String licenseValidUntil,
                                 @RequestParam(name="mvu",required = false) String medExValidUntil,
                                 HttpSession session, Map<String, Object> model)
    {
        String fullName=lName+" "+fName+" "+mName;

        Personnel worker=personnelRepo.findByPersonnelID(personnelID);

        worker.setFullName(fullName);
        worker.setAddress(address);
        worker.setPassportID(passportID);
        worker.setEducationDegree(educationDegree);
        worker.setPhoneNumber(phoneNumber);
        //worker.setEmployeePosition(employeePosition);
        /////
        worker.setPassword(password);

        ////
        worker.setTransportID(transportID);

        personnelRepo.save(worker);


        Driver_Data driverData=driverDataRepo.findByOwnerId(personnelID);

        if(driverData!=null)
        {
            //////////////////////
            String categories=new String();

            for(int i=0;i<licenseCategory.length;i++)
            {
                if(i==0)
                {
                    categories=licenseCategory[i];
                }
                else
                {
                    categories=categories+" "+licenseCategory[i];
                }

            }

            driverData.setLicenseNum(licenseNum);
            driverData.setLicenseIssuedBy(licenseIssuedBy);
            driverData.setLicenseCategory(categories);
            driverData.setLicenseValidUntil(licenseValidUntil);
            driverData.setMedExValidUntil(medExValidUntil);

            driverDataRepo.save(driverData);
        }

        return employeePage(personnelID,session,model);
    }

    @GetMapping("/adminportal/personnel/createworker")
    public String employeeCreationMenu(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("adminlogin");

        if(!navBarLoader.checkAuthorizationAdmin(login,model))
        {
            return "admin/main/AdminAuthorization";
        }

        Iterable<Transport> unassignedTransports=transportRepo.findAllUnassignedCars();
        model.put("carData",unassignedTransports);

        return "admin/EmployeeeCreation";
    }


    @PostMapping("/adminportal/personnel/createemployee")
    public String createEmployee(@RequestParam(name="name") String fName,@RequestParam(name="mname") String mName,@RequestParam(name="lname") String lName, @RequestParam(name="aor") String address,
                               @RequestParam(name="dob")String dateOfBirth, @RequestParam(name="pob")String placeOfBirth, @RequestParam(name="pn")String passportID,
                               @RequestParam(name="loe") String educationDegree,@RequestParam(name="not") String phoneNumber,@RequestParam(name = "pr") String employeePosition
                               /*@RequestParam(name = "lg") String login*/,@RequestParam(name = "psw") String password,@RequestParam(name="asc",required = false) Integer transportID,
                               @RequestParam(name="ln",required = false) String licenseNum,@RequestParam(name="ib",required = false) String licenseIssuedBy,@RequestParam(name="a",required = false) String[] licenseCategory,
                               @RequestParam(name="lvu",required = false) String licenseValidUntil,@RequestParam(name="mvu",required = false) String medExValidUntil,
                               /*@RequestParam(name="asc",required = false) Str,*/
                               HttpSession session, Map<String, Object> model)
    {
        String fullName=lName+" "+fName+" "+mName;
        ////Creation of login
        ////
        String login=lName+"_"+fName.charAt(0)+mName.charAt(0)+"_"+dateOfBirth.split("-")[0];



        Personnel newEmployee=new Personnel(fullName,dateOfBirth,placeOfBirth,passportID,address,
                educationDegree,phoneNumber,transportID,employeePosition,login,password);
        personnelRepo.save(newEmployee);

        System.out.println(licenseNum);

        if(licenseNum!=null)
        {
            String categories=new String();

            /////
            for(int i=0;i<licenseCategory.length;i++)
            {
                if(i==0)
                {
                    categories=licenseCategory[i];
                }
                else
                {
                    categories=categories+" "+licenseCategory[i];
                }

            }

            Driver_Data newDriverData=new Driver_Data(newEmployee.getPersonnelID(),licenseNum,licenseIssuedBy,categories,licenseValidUntil,medExValidUntil);
            driverDataRepo.save(newDriverData);
        }

        return employeePage(newEmployee.getPersonnelID(),session,model);
    }

}
