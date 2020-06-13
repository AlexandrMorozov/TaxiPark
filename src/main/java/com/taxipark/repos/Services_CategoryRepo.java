package com.taxipark.repos;

import com.taxipark.dbmodel.Services_Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Services_CategoryRepo extends CrudRepository<Services_Category,Integer>
{
    List<Services_Category> findAllByServiceCategoryType(String serviceCategoryType);


    @Query("SELECT new com.taxipark.dbmodel.Services_Category(sc.serviceCategoryID,sc.serviceCategoryName," +
            "sc.serviceCategoryType) FROM Services_Category sc")
    List<Services_Category> findAllCategories();

    Services_Category findByServiceCategoryID(int serviceCategoryID);

}
