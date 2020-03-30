package core;

import dbmodel.Services_Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Services_CategoryRepo extends CrudRepository<Services_Category,Integer>
{
    List<Services_Category> findAllByServiceCategoryType(String serviceCategoryType);

    @Query("SELECT new dbmodel.Services_Category(s.serviceCategoryID, s.serviceCategoryName) " +
            "FROM Services_Category s WHERE s.serviceCategoryType=:serviceType")
    List<Services_Category> findByServiceCategoryIDAndServiceCategoryName(@Param("serviceType")String serviceType);
}
