package core;

import dbmodel.Customer_Services_Data;
import dbmodel.Services;
import dto.CompletionTimeDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicesRepo extends CrudRepository<Services,Integer>
{
    List<Services> findAllByCategoryID(int categoryID);
    Services findByServicesID(int serviceID);

    @Query("SELECT new dto.CompletionTimeDto(s.servicesID,c.completionTime) FROM Customer_Services_Data c " +
            "INNER JOIN Services s ON s.customerServicesDataID=c.customerServicesDataID WHERE s.servicesID=:id")
    CompletionTimeDto findCompletionTime(@Param("id") int id);
}
