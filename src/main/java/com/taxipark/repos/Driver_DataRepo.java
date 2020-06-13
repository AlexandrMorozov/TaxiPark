package com.taxipark.repos;

import com.taxipark.dbmodel.Driver_Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Driver_DataRepo extends CrudRepository<Driver_Data,Integer>
{
}
