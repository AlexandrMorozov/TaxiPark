package com.taxipark.repos;

import com.taxipark.dbmodel.Driver_Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Driver_DataRepo extends CrudRepository<Driver_Data,Integer>
{
   // Driver_Data findByOwnerId(int ownerID);

   /* @Modifying
    @Query("UPDATE Driver_Data dd SET dd.licenseNum=?1,dd.licenseIssuedBy=?2," +
            "dd.licenseCategory=?3,dd.licenseValidUntil=?4,dd.medExValidUntil=?5")
    void updateDriverData(String licenseNum,String licenseIssuedBy,String licenseCategory,
                             String licenseValidUntil, String medExValidUntil);*/
}
