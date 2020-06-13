package com.taxipark.repos;

import com.taxipark.dbmodel.Services;
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

}
