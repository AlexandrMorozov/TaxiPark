package com.taxipark.repos;

import com.taxipark.dbmodel.Order_Route;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dto.RpBsDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

public interface PersonnelRepo extends CrudRepository<Personnel,Integer>
{
    Personnel findByPersonnelID(int personnelID);

    Personnel findByLogin(String login);

   // Personnel findAllByPassportID(String passportID);

    //int personnelID, String fullName,String employeePosition,String login
    //////////
    /*
    @Query("SELECT new com.taxipark.dbmodel.Personnel(d.personnelID, d.fullName,d.employeePosition,d.login) FROM Personnel d ")
    List<Personnel> findAllPersonnel();
    */

   /* @Query("SELECT new com.taxipark.dbmodel.Personnel(d.personnelID, d.fullName,p.positionName,d.login) FROM Personnel d " +
            "INNER JOIN com.taxipark.dbmodel.Positions p ON d.position=p.positionID")
    List<Personnel> findAllPersonnel();*/

   List<Personnel> findAll();

    Personnel findByLoginAndPassword(String login,String password);

    /*@Query("SELECT new com.taxipark.dbmodel.Personnel() FROM Personnel p " +
            "INNER JOIN com.taxipark.dbmodel.Transport t ON p.transportID=t.transportID")
    List<Personnel> f();*/

    /*@Query("SELECT new com.taxipark.dbmodel.Personnel(d.personnelID,d.login,d.fullName) FROM Personnel d WHERE d.transportID=null AND d.employeePosition=:pos")
    List<Personnel> findAllUnassignedDrivers(@Param("pos") String position);*/

    /*@Modifying
    @Query("UPDATE Personnel p SET p.fullName=:fullName, p.address=:address,p.passportID=:passportID,p.educationDegree=:educationDegree,p.phoneNumber=:phoneNumber" +
            ",p.transportID=:transportID WHERE p.personnelID=:personnelID")
    void updatePersonnel(@Param("fullName") String fullName,@Param("address") String address, @Param("passportID")String passportID,@Param("educationDegree") String educationDegree,
                            @Param("phoneNumber") String phoneNumber,@Param("transportID") Integer transportID,@Param("personnelID") int personnelID);



*/
   /* @Query()
    List<Personnel> */
}
