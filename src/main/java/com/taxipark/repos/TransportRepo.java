package com.taxipark.repos;

import com.taxipark.dbmodel.Transport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepo extends CrudRepository<Transport,Integer> {

   @Query("SELECT new com.taxipark.dbmodel.Transport(d.transportID,d.carBrand,d.carModel,d.vin)" +
           " FROM Transport d LEFT OUTER JOIN Personnel p " + "ON d.transportID=p.transportID WHERE p.transportID IS NULL")
    List<Transport> findAllUnassignedCars();

    Transport findByTransportID(Integer transportID);

    @Query("SELECT new com.taxipark.dbmodel.Transport(d.transportID,d.carBrand,d.carModel,d.vin,d.licensePlate) FROM Transport d ")
    List<Transport> findAllTransport();

}