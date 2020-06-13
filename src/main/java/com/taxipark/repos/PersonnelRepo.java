package com.taxipark.repos;

import com.taxipark.dbmodel.Order_Route;
import com.taxipark.dbmodel.Personnel;
import com.taxipark.dbmodel.Positions;
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
    List<Personnel> findAll();
    Personnel findByLoginAndPassword(String login,String password);
    List<Personnel> findByPersonnelProfession(Positions position);

    @Query("SELECT new com.taxipark.dbmodel.Personnel(p.personnelID,p.fullName,p.dateOfBirth,p.placeOfBirth, p.passportID," +
            "p.address, p.educationDegree, p.phoneNumber, p.transportID," +
            "p.personnelProfession,p.login, p.password) FROM Personnel p INNER JOIN com.taxipark.dbmodel.Transport t " +
            "ON p.transportID=t.transportID WHERE p.personnelProfession=:prof AND t.type=:tp")
    List<Personnel> findByProfessionAndTransportType(@Param("prof") Positions position,@Param("tp") String type);
}
