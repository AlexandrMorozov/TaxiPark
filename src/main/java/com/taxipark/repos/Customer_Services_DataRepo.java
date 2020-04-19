package com.taxipark.repos;

import com.taxipark.dbmodel.Customer_Services_Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public interface Customer_Services_DataRepo extends CrudRepository<Customer_Services_Data,Integer>
{
    Customer_Services_Data findByCustomerServicesDataID(int customerServicesDataID);

    @Modifying
    @Query("UPDATE Customer_Services_Data cs SET cs.completionTime=?1,cs.guaranteeTime=?2 WHERE cs.customerServicesDataID=?3")
    boolean updateCustomerServicesData(double completionTime, int guaranteeTime,int customerServicesDataID);
}
