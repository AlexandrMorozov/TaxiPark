package com.taxipark.repos;

import com.taxipark.dbmodel.Payments_List;
import com.taxipark.dto.StatisticsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Payments_ListRepo extends CrudRepository<Payments_List,Integer>
{
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,SUM(pl.paymentSum)) FROM Payments_List pl WHERE pl.paymentType=:paymentType " +
            "AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findSumOfAllPaymentsByDays(@Param("paymentType") String paymentType,@Param("frm") String from, @Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,SUM(pl.paymentSum)) FROM Payments_List pl WHERE pl.paymentType=:paymentType " +
            "AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth ORDER BY pl.paymentMonth")
    List<StatisticsDto> findSumOfAllPaymentsByMonths(@Param("paymentType") String paymentType,@Param("frm") String from,@Param("to") String to);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,COUNT(pl.paymentDate)) FROM Payments_List pl WHERE pl.paymentType=:paymentType" +
            " AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findNumberOfAllPaymentsByDays(@Param("paymentType") String paymentType,@Param("frm") String from,@Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,COUNT(pl.paymentDate)) FROM Payments_List pl WHERE pl.paymentType=:paymentType" +
            " AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth ORDER BY pl.paymentMonth")
    List<StatisticsDto> findNumberOfAllPaymentsByMonths(@Param("paymentType") String paymentType,@Param("frm") String from,@Param("to") String to);

    /*@Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,SUM(pl.paymentSum)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services sc ON pl.serviceID=sc.servicesID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceName=:servName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findSumOfPaymentsByServiceAndDays(@Param("paymentType") String paymentType,@Param("servName") String service,@Param("frm") String from,@Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,SUM(pl.paymentSum)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services sc ON pl.serviceID=sc.servicesID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceName=:servName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth ORDER BY pl.paymentMonth")
    List<StatisticsDto> findSumOfPaymentsByServiceAndMonths(@Param("paymentType") String paymentType,@Param("servName") String service,@Param("frm") String from,@Param("to") String to);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,COUNT(pl.paymentDate)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services sc ON pl.serviceID=sc.servicesID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceName=:servName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findNumberOfPaymentsByServiceAndDays(@Param("paymentType") String paymentType,@Param("servName") String service,@Param("frm") String from,@Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,COUNT(pl.paymentDate)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services sc ON pl.serviceID=sc.servicesID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceName=:servName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth ORDER BY pl.paymentMonth")
    List<StatisticsDto> findNumberOfPaymentsByServiceAndMonths(@Param("paymentType") String paymentType,@Param("servName") String service,@Param("frm") String from,@Param("to") String to);
*/
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,SUM(pl.paymentSum)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services_Category sc ON pl.serviceCategoryID=sc.serviceCategoryID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceCategoryName=:categoryName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findSumOfPaymentsByServiceCategoryAndDays(@Param("paymentType") String paymentType,@Param("categoryName") String categoryName,@Param("frm") String from,@Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,SUM(pl.paymentSum)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services_Category sc ON pl.serviceCategoryID=sc.serviceCategoryID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceCategoryName=:categoryName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth  ORDER BY pl.paymentMonth")
    List<StatisticsDto> findSumOfPaymentsByServiceCategoryAndMonths(@Param("paymentType") String paymentType,@Param("categoryName") String categoryName,@Param("frm") String from,@Param("to") String to);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentDate,COUNT(pl.paymentDate)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services_Category sc ON pl.serviceCategoryID=sc.serviceCategoryID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceCategoryName=:categoryName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentDate ORDER BY pl.paymentDate")
    List<StatisticsDto> findNumberOfPaymentsByServiceCategoryAndDays(@Param("paymentType") String paymentType,@Param("categoryName") String categoryName,@Param("frm") String from,@Param("to") String to);

    //
    @Query("SELECT new com.taxipark.dto.StatisticsDto(pl.paymentMonth,COUNT(pl.paymentDate)) FROM Payments_List pl INNER JOIN com.taxipark.dbmodel.Services_Category sc ON pl.serviceCategoryID=sc.serviceCategoryID" +
            " WHERE pl.paymentType=:paymentType AND sc.serviceCategoryName=:categoryName AND pl.paymentDate BETWEEN :frm AND :to GROUP BY pl.paymentMonth ORDER BY pl.paymentMonth")
    List<StatisticsDto> findNumberOfPaymentsByServiceCategoryAndMonths(@Param("paymentType") String paymentType,@Param("categoryName") String categoryName,@Param("frm") String from,@Param("to") String to);

    @Query("SELECT new com.taxipark.dbmodel.Payments_List(pl.paymentYear) FROM Payments_List pl GROUP BY pl.paymentYear ORDER BY pl.paymentYear ASC")
    List<Payments_List> findAllYears();


}
