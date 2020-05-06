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

    /*@Query("SELECT new com.taxipark.dbmodel.Services_Category(s.serviceCategoryID, s.serviceCategoryName) " +
            "FROM Services_Category s WHERE s.serviceCategoryType=:serviceType")
    List<Services_Category> findByServiceCategoryIDAndServiceCategoryName(@Param("serviceType")String serviceType);


*/
    @Query("SELECT new com.taxipark.dbmodel.Services_Category(sc.serviceCategoryID,sc.serviceCategoryName," +
            "sc.serviceCategoryType) FROM Services_Category sc")
    List<Services_Category> findAllCategories();

    Services_Category findByServiceCategoryID(int serviceCategoryID);


    /*@Modifying
    @Query("UPDATE Services_Category sc SET sc.serviceCategoryName=?1," +
            "sc.serviceCategoryDescription=?2,sc.serviceCategoryFoto=?3 WHERE sc.serviceCategoryID=?4")
    boolean updateServicesCategory(String serviceCategoryName, String serviceCategoryDescription,
                                   String serviceCategoryFoto,int serviceCategoryID);*/
}
