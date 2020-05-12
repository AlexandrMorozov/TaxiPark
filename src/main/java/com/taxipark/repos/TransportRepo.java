package com.taxipark.repos;

import com.taxipark.dbmodel.Order_Route;
import com.taxipark.dbmodel.Transport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.TabableView;
import java.util.List;

public interface TransportRepo extends CrudRepository<Transport,Integer> {
    /*@Modifying
    @Query("UPDATE Transport tr SET tr.carBrand=?1,tr.carModel=?2,tr.carColor=?3," +
            "tr.licensePlate=?4,tr.vin=?5,tr.carcassNum=?6,tr.engineNum=?7," +
            "tr.yearOfManufacture=?8,tr.otherAttributes=?9 WHERE tr.transportID=?10")
    void modifyTransport(String carBrand, String carModel, String carColor, String licensePlate, String vin, String carcassNum,
                            String engineNum, String yearOfManufacture, String otherAttributes, int transportID);*/



   @Query("SELECT new com.taxipark.dbmodel.Transport(d.transportID,d.carBrand,d.carModel,d.vin)" +
           " FROM Transport d LEFT OUTER JOIN Personnel p " + "ON d.transportID=p.transportID WHERE p.transportID IS NULL")
    List<Transport> findAllUnassignedCars();

    Transport findByTransportID(int transportID);

    @Query("SELECT new com.taxipark.dbmodel.Transport(d.transportID,d.carBrand,d.carModel,d.vin,d.licensePlate) FROM Transport d ")
    List<Transport> findAllTransport();

}