package core;

import dbmodel.Customer_Services_Data;
import org.springframework.data.repository.CrudRepository;

public interface Customer_Services_DataRepo extends CrudRepository<Customer_Services_Data,Integer>
{
    Customer_Services_Data findByCustomerServicesDataID(int customerServicesDataID);
}
