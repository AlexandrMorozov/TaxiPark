package com.taxipark.repos;

import com.taxipark.dbmodel.Services;
import com.taxipark.dto.CompletionTimeDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicesRepo extends CrudRepository<Services,Integer>
{
    List<Services> findAllByCategoryID(int categoryID);

    Services findByServicesID(int serviceID);

    @Query("SELECT new com.taxipark.dbmodel.Services(sv.servicesID,sv.serviceName,sv.price,sv.calculatablePrice)" +
            " FROM Services sv WHERE sv.categoryID=:catID")
    List<Services> findAllServicesByCategoryID(@Param("catID") int categoryID);

    @Query("SELECT new com.taxipark.dto.CompletionTimeDto(s.servicesID,c.completionTime) FROM Customer_Services_Data c " +
            "INNER JOIN Services s ON s.customerServicesDataID=c.customerServicesDataID WHERE s.servicesID=:id")
    CompletionTimeDto findCompletionTime(@Param("id") int id);

    @Modifying
    @Query("UPDATE Services ss SET ss.serviceName=?1,ss.serviceDescription=?2," +
            "ss.foto=?3,ss.price=?4,ss.calculatablePrice=?5 WHERE ss.servicesID=?6")
    boolean updateService(String serviceName,String serviceDescription,String foto,
                          double price, Double calculatablePrice,int servicesID);

}
